package com.want.want.activity.tree

import android.app.Application
import com.want.common.rv.QuickAdapter
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.launch
import com.want.network.util.loadSuccess
import com.want.network.util.noMoreData
import com.want.network.util.response
import com.want.want.R
import com.want.want.bean.*
import com.want.want.fragment.home.viewmodel.ItemHomeVM
import com.want.want.rv.RecyclerViewVM
import com.want.want.viewmodel.TitleViewModel

/**
 * Created by chengzf on 2021/6/4.
 */
class TreeListViewModel(app:Application):BaseRepositoryViewModel<TreeListRepository>(app,TreeListRepository()) {

    companion object {
        const val TITLE = "title"
        const val CID = "cid"
    }

    var mTitleVM = TitleViewModel(
        leftAction = {
            finish()
        },
        title = ""
    )

    private var mData = arrayListOf<ItemHomeVM>()
    private val mAdapter = QuickAdapter(R.layout.item_rv_home,mData)

    private var mCurrPage = 0
    private var mPageCount = 1
    private var mCid: Int? = null

    var rvVM = RecyclerViewVM(app).apply {
        mRefreshEnable = true
        mAdapterObservable.set(mAdapter)
        mOnRefresh = {
            mIsRefreshing.set(true)
            mCurrPage = 0
            requestServer(false)
        }

        mOnLoadMoreListener = {
            mCurrPage++
            if (mCurrPage<mPageCount){
                requestServer(true)
            }else{
                noMoreData()
            }
        }
    }

    override fun onModelBind() {
        super.onModelBind()
        mBundle.getString(TITLE)?.let {
            mTitleVM.mTitle.set(it)
        }
        mCid = mBundle.getInt(CID)
        requestServer(true)
    }

    private fun requestServer(showDialog: Boolean) {
        launch(showDialog){
            response(mRepo.articleList(mCurrPage,mCid)){
                onSuccess(showDialog)
            }
        }
    }

    fun updateCollectState(bean: CollectChangeBean) {
        mData.find { it.mId == bean.id }?.mCollect?.set(bean.isCollect)
    }

    private fun ObjectDataBean.onSuccess(showDialog: Boolean) {
        if (!showDialog) {
            mData.clear()
        }
        mPageCount = data?.pageCount ?: 1
        data?.datas?.forEach {
            bindData(it)
        }
        mAdapter.notifyDataSetChanged()
        if (!showDialog){
            rvVM.mIsRefreshing.set(false)
        }
        loadSuccess()
    }

    private fun bindData(bean: ItemDatasBean) {
        mData.add(ItemHomeVM(getApplication(), bean).apply {
            bindData()
            onCollectClick = {
                if (mCollect.get()) {
                    mId?.let {
                        unCollectDelegate(it, mRepo)
                    }
                } else {
                    mId?.let {
                        collectDelegate(it, mRepo)
                    }
                }
            }
        })

    }


}