package com.want.want.home.viewmodel

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.want.common.ItemType
import com.want.common.rv.BaseMultiItemViewModel
import com.want.common.rv.QuickMultiAdapter
import com.want.common.utils.getResDimen
import com.want.common.utils.showToast
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.loadSuccess
import com.want.network.util.noMoreData
import com.want.want.R
import com.want.want.adapter.BannerAdapter
import com.want.want.bean.*
import com.want.want.home.HomeRepository
import com.want.want.rv.RecyclerViewVM
import com.want.want.viewmodel.TitleViewModel
import com.youth.banner.transformer.DepthPageTransformer
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
        addType(R.layout.item_rv_home,ItemType.ITEM_HOME_MAIN)
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
                getArticleTop(refresh)
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

    fun updateCollectState(bean: CollectChangeBean) {
        mData.filterIsInstance<ItemHomeVM>()
            .find { it.mId == bean.id }?.mCollect?.set(bean.isCollect)
    }

    /**
     * 获取banner轮播图
     */
    private suspend fun getBannerImages(refresh: HomePageState) {
        if (refresh == HomePageState.INIT || refresh == HomePageState.REFRESH){
            mBannerBeanList.clear()
            mRepo.banner()?.data?.forEach{
                mBannerBeanList.add(BannerBean(it.imagePath,it.title,it.url))
            }
            mData.add(mHomeBannerVM)
        }
    }

    /**
     * 获取置顶文章
     */
    private suspend fun getArticleTop(refresh: HomePageState){
        if (refresh == HomePageState.INIT || refresh == HomePageState.REFRESH){
            mRepo.articleTop()?.data?.forEach {
                addTopTag(it)
                bindData(it)
            }
        }
    }

    private fun addTopTag(it: ItemDatasBean) {
        val tempTags = arrayListOf<ItemDatasBean.TagBean>()
        tempTags.add(ItemDatasBean.TagBean("置顶"))
        it.tags?.let { tag -> tempTags.addAll(tag) }
        it.tags = tempTags
    }

    private fun bindData(bran: ItemDatasBean) {
        mData.add(ItemHomeVM(getApplication(),bran).apply {
            bindData()
            onCollectClick = {
                if (mCollect.get()){
                    mId?.let {
                        unCollectDelegate(it,mRepo)
                    }
                }else {
                    mId?.let {
                        //收藏
                        collectDelegate(it,mRepo)
                    }
                }
            }
        })
    }

}
