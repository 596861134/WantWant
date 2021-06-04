package com.want.want.fragment.find.viewmodel

import android.app.Application
import com.want.common.rv.QuickAdapter
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.launch
import com.want.network.util.loadSuccess
import com.want.network.util.noMoreData
import com.want.want.R
import com.want.want.bean.CollectChangeBean
import com.want.want.bean.collectDelegate
import com.want.want.bean.unCollectDelegate
import com.want.want.fragment.find.repository.FindContentProjectRepository
import com.want.want.rv.RecyclerViewVM

class FindContentProjectViewModel(app: Application) :
    BaseRepositoryViewModel<FindContentProjectRepository>(app, FindContentProjectRepository()) {

    private var mData = arrayListOf<ItemFindContentProjectVM>()
    private val mAdapter = QuickAdapter(R.layout.item_rv_find_content_project, mData)

    private var mCurrPage = 0
    private var mPageCount = 1
    var isRequestSuccess = false

    var rvVM = RecyclerViewVM(app).apply {
        mRefreshEnable = true
        mAdapterObservable.set(mAdapter)

        mOnRefresh = {
            mIsRefreshing.set(true)
            mCurrPage = 0
            requestServer(true)
        }

        mOnLoadMoreListener = {
            mCurrPage++
            if (mCurrPage < mPageCount) {
                requestServer()
            } else {
                noMoreData()
            }
        }
    }

    fun updateCollectState(bean: CollectChangeBean) {
        mData.find { it.mId == bean.id }?.mCollect?.set(bean.isCollect)
    }

    fun requestServer(isRefresh: Boolean = false) {
        launch(showDialog = !isRefresh, finish = {
            if (isRefresh) {
                rvVM.mIsRefreshing.set(false)
            }
        }) {
            isRequestSuccess = true
            if (isRefresh) {
                mData.clear()
            }
            val data = mRepo.projectList(mCurrPage).data
            mPageCount = data?.pageCount ?: 1
            data?.datas?.forEach {
                mData.add(ItemFindContentProjectVM(getApplication()).apply {
                    mPath.set(it.envelopePic)
                    mTitle.set(it.title)
                    mDesc.set(it.desc)
                    mTime.set(it.niceShareDate)
                    mAuthor.set(it.author)
                    mCollect.set(it.collect ?: false)
                    mId = it.id
                    mLink = it.link
                    onCollectClick = {
                        if (mCollect.get()) {
                            mId?.let { id ->
                                unCollectDelegate(id, mRepo)
                            }
                        } else {
                            mId?.let { id ->
                                collectDelegate(id, mRepo)
                            }
                        }
                    }
                })
            }
            loadSuccess()
            mAdapter.notifyDataSetChanged()
        }

    }


}