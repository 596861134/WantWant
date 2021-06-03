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
import com.want.want.fragment.find.repository.FindContentWeChatRepository
import com.want.want.fragment.home.viewmodel.ItemHomeVM
import com.want.want.rv.RecyclerViewVM
import kotlinx.coroutines.launch

class FindContentWeChatViewModel(app:Application) : BaseRepositoryViewModel<FindContentWeChatRepository>(app,FindContentWeChatRepository()) {


    private var mLeftData = arrayListOf<ItemFindContentTreeAndNaviLeftVM>()
    private var mLeftAdapter = QuickAdapter(R.layout.item_rv_find_content_tree_and_navi_left,mLeftData)

    private var mRightData = arrayListOf<ItemHomeVM>()
    private var mRightAdapter = QuickAdapter(R.layout.item_rv_home,mRightData)

    var isRequestSuccess = false

    private var mCurrPage = 0
    private var mPageCount = 1
    private var mCurrId: Int? = 0
    private var mCollectId: Int? = null

    var rvVMLeft = RecyclerViewVM(app).apply {
        mAdapterObservable.set(mLeftAdapter)
    }

    var rvVMRight = RecyclerViewVM(app).apply {
        mRefreshEnable = true
        mAdapterObservable.set(mRightAdapter)
        mOnRefresh={
            mIsRefreshing.set(true)
            mCurrPage=0
            weChatListDetail(mCurrId,isRefresh=true)
        }

        mOnLoadMoreListener = {
            mCurrPage++
            if (mCurrPage<mPageCount){
                weChatListDetail(mCurrId)
            }else{
                noMoreData()
            }
        }
    }

    fun requestServer(){
        viewModelScope.launch {
            isDialogShow.value = true
            try {
                isRequestSuccess = true
                val data = mRepo.weChatList().data
                data?.forEach {
                    mLeftData.add(ItemFindContentTreeAndNaviLeftVM(getApplication()).apply {
                        mContent.set(it?.name)
                        mCid = it?.id
                        onClickItem={
                            if (mChecked.get().falsely()){
                                mLeftData.find { it.mChecked.get().truely() }?.apply {
                                    mChecked.set(false)
                                }
                                mChecked.set(true)
                                mCurrPage = 0
                                weChatListDetail(it?.id,isClick = true)
                            }
                        }
                    })
                }
                mLeftData[0].mChecked.set(true)
                weChatListDetail(data?.get(0)?.id)
                mLeftAdapter.notifyDataSetChanged()
            }catch (e:Exception){
                e.printStackTrace()
                e.netError()
                isRequestSuccess = false
                isDialogShow.value = false
            }
        }
    }

    fun updateCollectState(bean: CollectChangeBean) {
        mRightData.find { it.mId == bean.id }?.mCollect?.set(bean.isCollect)
    }

    private fun weChatListDetail(id: Int?, isClick: Boolean = false, isRefresh: Boolean = false) {
        mCurrId = id

        viewModelScope.launch {
            try {
                if (isRefresh){
                    mRightData.clear()
                }else{
                    isDialogShow.value = true
                    if (isClick){
                        mRightData.clear()
                    }
                }
                val data = mRepo.weChatListDetail(id, mCurrPage).data
                mPageCount = data?.pageCount ?: 1
                data?.datas?.forEach { it ->
                    mRightData.add(ItemHomeVM(getApplication(),it).apply {
                        bindData()
                        onCollectClick={
                            mCollectId = mId
                            if (mCollect.get()){
                                mId?.let {
                                    unCollectDelegate(it,mRepo)
                                }
                            }else{
                                mId?.let {
                                    collectDelegate(it,mRepo)
                                }
                            }
                        }
                    })
                }
                mRightAdapter.notifyDataSetChanged()
                if (!isRefresh){
                    loadSuccess()
                }

            }catch (e:Exception){
                e.printStackTrace()
                e.netError()
            }finally {
                if (isRefresh){
                    rvVMRight.mIsRefreshing.set(false)
                }else{
                    isDialogShow.value = false
                }
            }
        }
    }

}