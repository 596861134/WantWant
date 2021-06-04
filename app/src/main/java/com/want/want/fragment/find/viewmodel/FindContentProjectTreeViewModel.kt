package com.want.want.fragment.find.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.want.common.rv.QuickAdapter
import com.want.common.utils.falsely
import com.want.common.utils.truely
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.loadSuccess
import com.want.network.util.netError
import com.want.network.util.noMoreData
import com.want.want.R
import com.want.want.bean.CollectChangeBean
import com.want.want.bean.collectDelegate
import com.want.want.bean.unCollectDelegate
import com.want.want.fragment.find.repository.FindContentProjectTreeRepository
import com.want.want.rv.RecyclerViewVM
import kotlinx.coroutines.launch

class FindContentProjectTreeViewModel(app: Application) :
    BaseRepositoryViewModel<FindContentProjectTreeRepository>(
        app,
        FindContentProjectTreeRepository()
    ) {

    private var mLeftData = arrayListOf<ItemFindContentTreeAndNaviLeftVM>()
    private var mLeftAdapter =
        QuickAdapter(R.layout.item_rv_find_content_tree_and_navi_left, mLeftData)

    private var mRightData = arrayListOf<ItemFindContentProjectTreeRightVM>()
    private var mRightAdapter =
        QuickAdapter(R.layout.item_rv_find_content_project_tree_right, mRightData)

    private var mCurrPage = 0
    private var mPageCount = 1

    var isRequestSuccess = false
    private var mId: Int? = null
    private var mCollectId: Int? = null

    var leftVM = RecyclerViewVM(app).apply {
        mAdapterObservable.set(mLeftAdapter)
    }

    var rightVM = RecyclerViewVM(app).apply {
        mRefreshEnable = true
        mAdapterObservable.set(mRightAdapter)
        mOnRefresh = {
            mIsRefreshing.set(true)
            mCurrPage = 0
            projectList(mId, isRefresh = true)
        }

        mOnLoadMoreListener = {
            mCurrPage++
            if (mCurrPage < mPageCount) {
                projectList(mId)
            } else {
                noMoreData()
            }
        }
    }

    fun requestServer() {
        viewModelScope.launch {
            try {
                isDialogShow.value = true
                isRequestSuccess = true
                mRepo.projectTreeList().data?.forEach {
                    mLeftData.add(ItemFindContentTreeAndNaviLeftVM(getApplication()).apply {
                        mContent.set(it.name)
                        mCid = it.id
                        onClickItem = {
                            if (mChecked.get().falsely()) {
                                mLeftData.find { it.mChecked.get().truely() }?.mChecked?.set(false)
                                mChecked.set(true)
                                projectList(mCid, isClick = true)
                            }
                        }
                    })
                }

                val item = mLeftData[0]
                item.mChecked.set(true)
                mLeftAdapter.notifyDataSetChanged()
                projectList(item.mCid)
            } catch (e: Exception) {
                e.printStackTrace()
                e.netError()
            }
        }

    }

    private fun projectList(id: Int?, isRefresh: Boolean = false, isClick: Boolean = false) {
        mId = id
        viewModelScope.launch {
            try {
                if (isRefresh) {
                    mRightData.clear()
                } else {
                    if (isClick) {
                        mRightData.clear()
                    }
                    isDialogShow.value = true
                }

                val data = mRepo.projectListCid(mCurrPage, id).data
                mPageCount = data?.pageCount ?: 1
                data?.datas?.forEach {
                    mRightData.add(ItemFindContentProjectTreeRightVM(getApplication()).apply {
                        mPath.set(it.envelopePic)
                        mTitle.set(it.title)
                        mDesc.set(it.desc)
                        mTime.set(it.niceShareDate)
                        mAuthor.set(it.author)
                        mId = it.id
                        mLink = it.link
                        mCollect.set(it.collect ?: false)
                        onClickItem = {
                            mCollectId = mId
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

                if (!isRefresh) {
                    loadSuccess()
                }

                mRightAdapter.notifyDataSetChanged()

            } catch (e: Exception) {
                e.printStackTrace()
                e.netError()
            } finally {
                if (isRefresh) {
                    rightVM.mIsRefreshing.set(false)
                } else {
                    isDialogShow.value = false
                }
            }
        }
    }

    fun updateCollectState(bean: CollectChangeBean) {
        mRightData.find { it.mId == bean.id }?.mCollect?.set(bean.isCollect)
    }


}