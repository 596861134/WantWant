package com.want.want.fragment.collect

import android.app.Application
import android.content.Context
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.KeyboardUtils
import com.want.common.CommonUtil
import com.want.common.utils.isNotNull
import com.want.common.utils.logWithTag
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.activity.main.MainActivity
import com.want.want.bean.CollectChangeBean
import com.want.want.common.CollectContentPage
import com.want.want.common.EditPage
import com.want.want.databinding.CollectionFragmentBinding
import com.want.want.fragment.collect.viewmodel.CollectContentViewModel
import com.want.want.rv.RvScrollToTop
import com.want.want.utils.DialogUtil
import com.want.want.utils.GlobalSingle
import com.want.want.utils.RvScrollDelegate
import com.want.want.utils.SelectPage
import com.want.want.view.EditDialog

class CollectContentFragment : BaseVMRepositoryFragment<CollectContentViewModel,CollectionFragmentBinding>(R.layout.collect_content_fragment),
    RvScrollToTop, SelectPage {

    companion object {
        const val CONTENT_PAGE = "content_page"
    }

    private val mDialog by lazy { EditDialog(mActivity) }
    private var mContentPage:CollectContentPage? = null
    private var mFragmentInit:Boolean = false
    private var isTabLayoutClick:Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContentPage = arguments?.getSerializable(CONTENT_PAGE) as? CollectContentPage ?: CollectContentPage.NONE
        "onAttach mContentPage = $mContentPage".logWithTag(CommonUtil.TAG)
    }

    override fun getViewModel(app: Application): CollectContentViewModel = CollectContentViewModel(mContentPage!!, app)

    override fun onViewInit() {
        super.onViewInit()
        "onViewInit  this  = $this ".logWithTag(CommonUtil.TAG)
        mFragmentInit = true
        bindScrollListener()
    }

    override fun onEvent() {
        super.onEvent()
        GlobalSingle.isLoginSuccessToCollect.observe(this, Observer {
            if (it == mContentPage){
                mRealVM.requestServer()
                GlobalSingle.isLoginSuccessToCollect.value = CollectContentPage.NONE
            }
        })

        if (isTabLayoutClick){
            onSelectPage()
        }

        mRealVM.mDelWebsite.observe(this, Observer { id ->
            if (id.isNotNull()){
                DialogUtil.showDialog(mActivity,message = "你确定要删除吗？", positionAction = {
                    mRealVM.delCollectWebsite(id!!)
                    mRealVM.mDelWebsite.value = null
                    it.dismiss()
                })
            }
        })

        mRealVM.mDeleteShareArticle.observe(this, Observer { it ->
            if (it){
                DialogUtil.showDialog(mActivity,message = "你确定要删除吗？", positionAction = {
                    mRealVM.delMyArticle()
                    mRealVM.mDeleteShareArticle.value = false
                    it.dismiss()
                })
            }
        })

        GlobalSingle.showEditDialog.observe(this, Observer {
            if (mContentPage == it.collectContentPage){
                when(it.collectContentPage){
                    CollectContentPage.COLLECT_WEBSITE -> {
                        if (it.page != EditPage.NONE){
                            mDialog.showDialog(it.page,it.bean,it.collectContentPage)
                        }else{
                            mDialog.dismiss()
                            KeyboardUtils.hideSoftInputByToggle(mActivity)
                            if (it.bean.isNotNull()){
                                mRealVM.updateWebsiteChangeItem(it.bean!!)
                            }
                        }
                    }
                }
            }
        })

        GlobalSingle.onAddCollectArticle.observe(this, Observer {
            if (it==mContentPage){
                mRealVM.requestServer(false)
                GlobalSingle.onAddCollectArticle.value = CollectContentPage.NONE
            }
        })

        GlobalSingle.onAddShareArticle.observe(this, Observer {
            if (it == mContentPage){
                mRealVM.requestServer(false)
                GlobalSingle.onAddShareArticle.value = CollectContentPage.NONE
            }
        })

        GlobalSingle.onCollectPageSelect.observe(this, Observer {
            if (it == mContentPage){
                onSelectPage()
            }
        })
    }

    private val mObserver = Observer<CollectChangeBean>{
        when(mContentPage){
            CollectContentPage.INTERVIEW_RELATE -> mRealVM.updateCollectState(it)
            CollectContentPage.COLLECT_ARTICLE -> mRealVM.requestServer(false)
            else -> {}
        }
    }

    private val mAddWebsiteObserver = Observer<Boolean>{
        if (it){
            if (mContentPage == CollectContentPage.COLLECT_WEBSITE){
                mRealVM.collectWebsite(true)
                GlobalSingle.onAddCollectWebsite.value = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        GlobalSingle.onCollectChange.observe(this,mObserver)
        GlobalSingle.onAddCollectWebsite.observe(this,mAddWebsiteObserver)
    }

    override fun onPause() {
        super.onPause()
        GlobalSingle.onCollectChange.removeObserver(mObserver)
        GlobalSingle.onAddCollectWebsite.removeObserver(mAddWebsiteObserver)
    }

    override fun bindScrollListener() {
        RvScrollDelegate.bindScrollListener(mActivity as MainActivity, mRealVM.rvVM)
    }

    override fun scrollToTop() {
        RvScrollDelegate.scrollToTop(mRealVM.rvVM)
    }

    override fun onSelectPage() {
        if (!mFragmentInit){
            isTabLayoutClick = true
        }else{
            if (!mRealVM.isRequestSuccess){
                mRealVM.requestServer()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        "onDestroyView  mContentPage = $mContentPage".logWithTag(CommonUtil.TAG)
    }

}