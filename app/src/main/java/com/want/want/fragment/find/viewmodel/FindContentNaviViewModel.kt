package com.want.want.fragment.find.viewmodel

import android.app.Application
import com.want.common.rv.QuickAdapter
import com.want.common.utils.falsely
import com.want.common.utils.truely
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.launch
import com.want.want.R
import com.want.want.bean.ItemDatasBean
import com.want.want.fragment.find.repository.FindContentNaviRepository
import com.want.want.rv.RecyclerViewVM

class FindContentNaviViewModel(app:Application) : BaseRepositoryViewModel<FindContentNaviRepository>(app,FindContentNaviRepository()) {

    private var mLeftData = arrayListOf<ItemFindContentTreeAndNaviLeftVM>()
    private var mLeftAdapter = QuickAdapter(R.layout.item_rv_find_content_tree_and_navi_left,mLeftData)

    private var mRightData = arrayListOf<ItemFindContentTreeAndNaviRightVM>()
    private var mRightAdapter = QuickAdapter(R.layout.item_rv_find_content_tree_and_navi_right,mRightData)

    private val mNaviMap = hashMapOf<Int?,List<ItemDatasBean>?>()

    var isRequestSuccess = false

    var rvVMLeft = RecyclerViewVM(app).apply {
        mAdapterObservable.set(mLeftAdapter)
    }
    var rvVMRight = RecyclerViewVM(app).apply {
        mAdapterObservable.set(mRightAdapter)
    }

    fun requestServer(){
        launch {
            naviList()
        }
    }

    private suspend fun naviList() {
        isRequestSuccess = true
        mRepo.naviList().data?.forEach {
            mNaviMap[it.cid] = it.articles
            mLeftData.add(ItemFindContentTreeAndNaviLeftVM(getApplication()).apply {
                mContent.set(it.name)
                mCid = it.cid
                onClickItem = {
                    if (mChecked.get().falsely()){
                        mLeftData.find { it.mChecked.get().truely() }?.apply {
                            mChecked.set(false)
                        }
                        mChecked.set(true)
                    }
                    getNaviArticles(mCid)

                }
            })
        }

        val leftVM = mLeftData[0]
        leftVM.mChecked.set(true)
        getNaviArticles(leftVM.mCid)
        mLeftAdapter.notifyDataSetChanged()
    }

    private fun getNaviArticles(cid: Int?) {
        mRightData.clear()
        mNaviMap[cid]?.forEach {
            mRightData.add(ItemFindContentTreeAndNaviRightVM(getApplication(),true).apply {
                mContent.set(it.title)
                mCid = it.id
                mLink = it.link
            })
        }
        mRightAdapter.notifyDataSetChanged()
    }

}