package com.want.want.fragment.find.viewmodel

import android.app.Application
import com.want.common.rv.QuickAdapter
import com.want.common.utils.falsely
import com.want.common.utils.truely
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.launch
import com.want.want.R
import com.want.want.bean.TreeListBean
import com.want.want.fragment.find.repository.FindContentTreeRepository
import com.want.want.rv.RecyclerViewVM

class FindContentTreeViewModel(app:Application) : BaseRepositoryViewModel<FindContentTreeRepository>(app,FindContentTreeRepository()) {

    private var mLeftData = arrayListOf<ItemFindContentTreeAndNaviLeftVM>()
    private var mLeftAdapter = QuickAdapter(R.layout.item_rv_find_content_tree_and_navi_left,mLeftData)

    private var mRightData = arrayListOf<ItemFindContentTreeAndNaviRightVM>()
    private var mRightAdapter = QuickAdapter(R.layout.item_rv_find_content_tree_and_navi_right,mRightData)

    private val mTreeMap = hashMapOf<Int?,List<TreeListBean.DataBean.ChildrenBean?>?>()
    var isRequestSuccess = false

    var rvVMLeft = RecyclerViewVM(app).apply {
        mAdapterObservable.set(mLeftAdapter)
    }
    var rvVMRight = RecyclerViewVM(app).apply {
        mAdapterObservable.set(mRightAdapter)
    }

    fun requestServer(){
        launch { treeList() }
    }

    private suspend fun treeList() {
        isRequestSuccess = true
        mLeftData.clear()
        mRepo.treeList().data?.forEach {
            mTreeMap[it?.id] = it?.children
            mLeftData.add(ItemFindContentTreeAndNaviLeftVM(getApplication()).apply {
                mContent.set(it?.name)
                mCid = it?.id
                onClickItem={
                    if (mChecked.get().falsely()){
                        mLeftData.find { it.mChecked.get().truely() }?.apply {
                            mChecked.set(false)
                        }
                        mChecked.set(true)
                        getTreeArticles(mCid)
                    }
                }

            })
        }

        val leftVM = mLeftData[0]
        leftVM.mChecked.set(true)
        getTreeArticles(leftVM.mCid)
        mLeftAdapter.notifyDataSetChanged()

    }

    private fun getTreeArticles(cid: Int?) {
        mRightData.clear()
        mTreeMap[cid]?.forEach {
            mRightData.add(ItemFindContentTreeAndNaviRightVM(getApplication()).apply {
                mContent.set(it?.name)
                mCid = it?.id
            })
        }
        mRightAdapter.notifyDataSetChanged()
    }

}
