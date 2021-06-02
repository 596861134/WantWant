package com.want.want.fragment.collect

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.want.common.CommonUtil
import com.want.common.ItemType
import com.want.common.rv.BaseMultiItemViewModel
import com.want.common.rv.QuickMultiAdapter
import com.want.common.utils.logWithTag
import com.want.common.utils.showToast
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.launch
import com.want.network.util.loginFirst
import com.want.network.util.noMoreData
import com.want.network.util.response
import com.want.want.AppApplication
import com.want.want.R
import com.want.want.activity.login.LoginActivity
import com.want.want.activity.login.viewmodel.LoginViewModel
import com.want.want.bean.*
import com.want.want.common.CollectContentPage
import com.want.want.common.EditDialogEvent
import com.want.want.common.EditDialogEventBean
import com.want.want.common.EditPage
import com.want.want.fragment.home.viewmodel.ItemHomeVM
import com.want.want.rv.RecyclerViewVM
import com.want.want.utils.GlobalSingle

class CollectContentViewModel(private val mContentPage: CollectContentPage, app: Application) :
    BaseRepositoryViewModel<CollectContentRepository>(app, CollectContentRepository()) {

    private var mData = mutableListOf<BaseMultiItemViewModel>()
    private val mAdapter = QuickMultiAdapter(mData).apply {
        addType(R.layout.item_rv_collect_website, ItemType.ITEM_COLLECT_WEBSITE)
        addType(R.layout.item_rv_home, ItemType.ITEM_HOME_MAIN)
    }

    private var mCurrentPage = 0
    private var mPageCount = 1
    private var mId: Int? = null

    var isRequestSuccess = false
    var mDeleteShareArticle = MutableLiveData<Boolean>()
    val mDelWebsite = MutableLiveData<Int?>()

    var rvVM = RecyclerViewVM(app).apply {
        mRefreshEnable = (mContentPage != CollectContentPage.SHARE_PROJECT)
        mAdapterObservable.set(mAdapter)
        mOnRefresh = {
            mIsRefreshing.set(true)
            mCurrentPage = 0
            requestServer(false)
        }

        mOnLoadMoreListener = {
            mCurrentPage++
            if (mCurrentPage < mPageCount) {
                requestServer(true)
            } else {
                noMoreData()
            }
        }
    }

    fun updateCollectState(bean: CollectChangeBean) {
        mData.filterIsInstance<ItemHomeVM>().find { it.mId == bean.id }?.mCollect?.set(bean.isCollect)
    }

    fun requestServer(showDialog: Boolean = true) {
        if (!AppApplication.isLogin && mContentPage != CollectContentPage.INTERVIEW_RELATE) {
            rvVM.mIsRefreshing.set(false)
            loginFirst()
            startActivity(LoginActivity::class.java, LoginViewModel.COLLECT_CONTENT_PAGE to mContentPage)
            return
        }

        when (mContentPage) {
            CollectContentPage.COLLECT_ARTICLE -> {
                launch(showDialog) {
                    response(mRepo.collectArticle(mCurrentPage)) {
                        onSuccess(showDialog)
                    }
                }
            }
            CollectContentPage.INTERVIEW_RELATE -> {
                launch(showDialog) {
                    response(mRepo.interviewRelate(mCurrentPage)) {
                        onSuccess(showDialog)
                    }
                }
            }
            CollectContentPage.SHARE_ARTICLE -> {
                launch(showDialog) {
                    response(mRepo.shareArticle(mCurrentPage)) {
                        data?.onSuccess(showDialog)
                    }
                }
            }
            CollectContentPage.COLLECT_WEBSITE -> {
                collectWebsite(showDialog)
            }
            else -> {
            }
        }
    }

    private fun ObjectDataBean.onSuccess(showDialog: Boolean) {
        if (!showDialog) {
            mData.clear()
        }
        isRequestSuccess = true
        mPageCount = data?.pageCount ?: 1
        data?.datas?.forEach {
            if (mContentPage == CollectContentPage.COLLECT_ARTICLE) {
                it.collect = true
            }
            bindData(it)
        }
        mAdapter.notifyDataSetChanged()
        if (!showDialog) {
            rvVM.mIsRefreshing.set(false)
        }
    }

    private fun bindData(bean: ItemDatasBean) {
        mData.add(ItemHomeVM(getApplication(), bean).apply {
            mCollectIconShow.set(!(mContentPage == CollectContentPage.SHARE_ARTICLE || mContentPage == CollectContentPage.SHARE_PROJECT))
            bindData()
            onCollectClick = {
                if (mCollect.get()) {
                    mId?.let {
                        unCollectDelegate(it, mRepo, isOnMe = mContentPage == CollectContentPage.COLLECT_ARTICLE, originId = mOriginId)
                    }
                } else {
                    mId?.let { collectDelegate(it, mRepo) }
                }
            }

            if (mContentPage == CollectContentPage.SHARE_ARTICLE) {
                onDelClick = {
                    this@CollectContentViewModel.mId = mId
                    mDeleteShareArticle.value = true
                }
            }
        })
    }


    fun updateWebsiteChangeItem(bean: EditDialogEventBean) {
        mData.filterIsInstance<ItemCollectWebsiteVM>().find { it.mId == bean.id }?.apply {
                mTitle.set(bean.name)
                mLink.set(bean.link)
            }
    }

    fun collectWebsite(showDialog: Boolean) {
        launch(showDialog) {
            response(mRepo.collectWebsite()) {
                rvVM.mIsRefreshing.set(false)
                mData.clear()
                isRequestSuccess = true
                data?.forEach {
                    mData.add(ItemCollectWebsiteVM(getApplication()).apply {
                        mId = it.id
                        mTitle.set(it.name)
                        mLink.set(it.link)
                        onEdit = {
                            GlobalSingle.showEditDialog.value = EditDialogEvent(
                                page = EditPage.WEBSITE,
                                bean = EditDialogEventBean(mId, mTitle.get(), mLink.get()),
                                collectContentPage = mContentPage
                            )
                        }
                        onDel = {
                            mDelWebsite.value = mId
                        }
                    })
                }
                mAdapter.notifyDataSetChanged()
            }
        }
    }

    fun delCollectWebsite(id: Int) {
        launch {
            response(mRepo.delCollectWebsite(id)) {
                val websiteData = mData.filterIsInstance<ItemCollectWebsiteVM>()
                mAdapter.removeAt(websiteData.indexOf(websiteData.find { it.mId == id }))
                "删除完成".showToast()
            }
        }
    }

    fun delMyArticle() {
        launch {
            response(mRepo.delMyArticle(mId)) {
                val itemList = mData.filterIsInstance<ItemHomeVM>()
                mAdapter.removeAt(itemList.indexOf(itemList.find { it.mId == mId }))
                "删除完成".showToast()
            }
        }
    }

}