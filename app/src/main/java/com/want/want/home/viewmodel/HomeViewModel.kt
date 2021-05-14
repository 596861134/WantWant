package com.want.want.home.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.want.common.ItemType
import com.want.common.rv.BaseMultiItemViewModel
import com.want.common.rv.QuickMultiAdapter
import com.want.common.utils.getResColor
import com.want.common.utils.getResDimen
import com.want.common.utils.logWithTag
import com.want.common.utils.showToast
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.TAG
import com.want.network.util.loadSuccess
import com.want.network.util.noMoreData
import com.want.want.R
import com.want.want.adapter.BannerAdapter
import com.want.want.bean.BannerBean
import com.want.want.bean.BannerViewModel
import com.want.want.home.HomeRepository
import com.want.want.rv.RecyclerViewVM
import com.want.want.viewmodel.TitleViewModel
import com.youth.banner.transformer.*
import kotlinx.coroutines.launch

enum class HomePageState{
    INIT,
    REFRESH,
    LOAD_MORE
}

class HomeViewModel(app:Application): BaseRepositoryViewModel<HomeRepository>(app, HomeRepository()) {

    var mTitleVM = TitleViewModel(
        leftDrawable = null,
        title = "首页",
    )

    private val mBannerBeanList = mutableListOf<BannerBean>()
    private val mBannerAdapter = BannerAdapter(mBannerBeanList)
    private val mBannerViewModel = BannerViewModel(app).apply {
        mBannerHeight.set(R.dimen.dp_200.getResDimen().toInt())
        mPageTransformer.set(DepthPageTransformer())
        mAdapterObservable.set(mBannerAdapter)
    }
    private val mHomeBannerVM = HomeBannerVM(app).apply {
        mBannerVM.set(mBannerViewModel)
    }

    private var mData = arrayListOf<BaseMultiItemViewModel>()
    private val mAdapter = QuickMultiAdapter(mData).apply {
        addType(R.layout.item_rv_home_banner, ItemType.ITEM_HOME_BANNER)
    }

    private var mCurrPage = 0
    private var mTotalPage = 1

    private var mIsInitUser = false

    var rvVM = RecyclerViewVM(app).apply {
        mRefreshEnable = true
        mAdapterObservable.set(mAdapter)

        mOnRefresh = {
            mIsRefreshing.set(true)
            mData.clear()
            mCurrPage = 0
            requestServer(HomePageState.REFRESH)
        }

        mOnLoadMoreListener = {
            mCurrPage++
            if (mCurrPage < mTotalPage){
                requestServer(HomePageState.LOAD_MORE)
            }else{
                noMoreData()
            }
        }
    }

    override fun onModelBind() {
        super.onModelBind()
        requestServer(HomePageState.INIT)
    }

    private fun requestServer(refresh: HomePageState) {
        viewModelScope.launch {
            try {
                resetDataIfNeed(refresh)
                dialogState(refresh,true)
                getBannerImages(refresh)
                dialogState(refresh,false)
            }catch (e:Exception){
                dialogState(refresh, isShow = false, success = false)
                e.message?.showToast()
            }finally {
                mAdapter.notifyDataSetChanged()
                hideRefreshLoading(refresh)
            }
        }
    }

    private fun resetDataIfNeed(state: HomePageState) {
        if (state == HomePageState.REFRESH) {
            mData.clear()
        }
    }

    private fun dialogState(state: HomePageState, isShow: Boolean, success: Boolean = true) {
        if (state != HomePageState.REFRESH) {
            isDialogShow.value = isShow
            if (!isShow && success) {
                loadSuccess()
            }
        }
    }

    private fun hideRefreshLoading(state: HomePageState) {
        if (state == HomePageState.REFRESH) {
            rvVM.mIsRefreshing.set(false)
        }
    }

    private suspend fun getBannerImages(refresh: HomePageState) {
        if (refresh == HomePageState.INIT || refresh == HomePageState.REFRESH){
            mBannerBeanList.clear()
            mRepo.banner()?.data?.forEach{
                mBannerBeanList.add(BannerBean(it.imagePath,it.title,it.url))
            }
            mData.add(mHomeBannerVM)
        }
    }



}
