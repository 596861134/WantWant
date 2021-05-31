package com.want.want.view

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.want.common.utils.isNull
import com.want.common.utils.showToast
import com.want.common.viewmodel.BaseLayoutViewModel
import com.want.network.util.launch
import com.want.network.util.response
import com.want.want.common.CollectContentPage
import com.want.want.common.EditDialogEvent
import com.want.want.common.EditDialogEventBean
import com.want.want.common.EditPage
import com.want.want.network.NetService
import com.want.want.utils.GlobalSingle

/**
 * Created by chengzf on 2021/5/31.
 */
class EditDialogViewModel(app:Application):BaseLayoutViewModel(app) {

    var mEditName = ObservableField("")

    var mTitle = ObservableField("")
    var mTitleHint = ObservableField("")

    var mAuthor = ObservableField("")
    var mAuthorHint = ObservableField("")
    var mAuthorVisible = ObservableField(true)

    var mLink = ObservableField("")
    var mLinkHint = ObservableField("")

    var mId: Int? = null
    private var mCollectContentPage: CollectContentPage = CollectContentPage.COLLECT_ARTICLE
    private var mCurrPage = EditPage.COLLECT_ARTICLE

    var mTitleInit = MutableLiveData<Boolean>()

    var mSelect = ObservableField(0)

    fun onClose(){
        GlobalSingle.showEditDialog.value = EditDialogEvent(page = EditPage.NONE,collectContentPage = mCollectContentPage)
    }

    fun handlePageData(page: EditPage, bean: EditDialogEventBean? = null, collectContentPage: CollectContentPage){
        mCurrPage = page
        mCollectContentPage = collectContentPage
        when(page){
            EditPage.COLLECT_ARTICLE -> {
                mEditName.set("收藏文章")
                mTitleHint.set("*请输入标题")
                mAuthorHint.set("请输入作者")
                mLinkHint.set("请输入链接地址")
                mAuthorVisible.set(true)
                mTitle.set(bean?.name)
                mLink.set(bean?.link)
                mId = bean?.id
            }

            EditPage.WEBSITE -> {
                mEditName.set(if (bean?.id.isNull()) "收藏网站" else "编辑网站")
                mTitleHint.set("*请输入网站名称")
                mLinkHint.set("请输入链接地址")
                mAuthorVisible.set(false)
                mTitle.set(bean?.name)
                mLink.set(bean?.link)
                mId = bean?.id
            }

            EditPage.SHARE_ARTICLE -> {
                mEditName.set("分享文章")
                mTitleHint.set("*请输入文章标题")
                mLinkHint.set("请输入文章链接")
                mAuthorVisible.set(false)
                mTitle.set(bean?.name)
                mLink.set(bean?.link)
                mId = bean?.id
            }

            else -> {}
        }

        if (!mTitle.get().isNullOrEmpty()){
            mSelect.set(mTitle.get()?.length)
        }
    }

    fun onConfirm(){
        if (mTitle.get().isNullOrEmpty()){
            mTitleHint.get()?.showToast()
            return
        }

        if (mLink.get().isNullOrEmpty()){
            mLinkHint.get()?.showToast()
            return
        }

        when(mCurrPage){
            EditPage.COLLECT_ARTICLE -> {
                collectAdd()
            }

            EditPage.WEBSITE -> {
                if (mId.isNull()){
                    collectWebsite()
                }else{
                    updateCollectWebsite()
                }
            }

            EditPage.SHARE_ARTICLE -> {
                addMyArticle()
            }

            else -> {}
        }
    }

    fun resetFiled(){
        mTitle.set("")
        mAuthor.set("")
        mLink.set("")
    }


    /**
     * 收藏文章
     */
    private fun collectAdd() {
        launch {
            response(NetService.api.collectAdd(mTitle.get(),mAuthor.get(),mLink.get())) {
                GlobalSingle.showEditDialog.value = EditDialogEvent(page = EditPage.NONE,collectContentPage = mCollectContentPage)
                GlobalSingle.onAddCollectArticle.value = mCollectContentPage
                resetFiled()
                "收藏文章成功。。。".showToast()
            }
        }
    }

    /**
     * 收藏网站
     */
    private fun collectWebsite() {
        launch {
            response(NetService.api.addCollectWebsite(mTitle.get(),mLink.get())){
                GlobalSingle.showEditDialog.value = EditDialogEvent(page = EditPage.NONE,collectContentPage = mCollectContentPage)
                GlobalSingle.onAddCollectWebsite.value = true
                resetFiled()
                "收藏网站成功。。。".showToast()
            }
        }
    }

    /**
     * 更新收藏网站
     */
    private fun updateCollectWebsite() {
        launch {
            response(NetService.api.updateCollectWebsite(id = mId, name = mTitle.get(), link = mLink.get())){
                GlobalSingle.showEditDialog.value = EditDialogEvent(collectContentPage = mCollectContentPage,
                bean = EditDialogEventBean(id = mId, name = mTitle.get(), link = mLink.get()),page = EditPage.NONE)
                resetFiled()
                "更新网站成功。。。".showToast()
            }
        }
    }

    private fun addMyArticle() {
        launch {
            response(NetService.api.addMyArticle(title = mTitle.get(),link = mLink.get())){
                GlobalSingle.showEditDialog.value = EditDialogEvent(page = EditPage.NONE,collectContentPage = mCollectContentPage)
                GlobalSingle.onAddShareArticle.value = mCollectContentPage
                resetFiled()
                "分享文章成功。。。".showToast()
            }
        }
    }
}
