package com.want.want.fragment.find.ui

import android.app.Application
import androidx.lifecycle.Observer
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.activity.main.MainActivity
import com.want.want.bean.CollectChangeBean
import com.want.want.databinding.FindContentWeChatFragmentBinding
import com.want.want.fragment.find.viewmodel.FindContentWeChatViewModel
import com.want.want.rv.RvScrollToTop
import com.want.want.utils.GlobalSingle
import com.want.want.utils.RvScrollDelegate
import com.want.want.utils.SelectPage

class FindContentWeChatFragment
    : BaseVMRepositoryFragment<FindContentWeChatViewModel,FindContentWeChatFragmentBinding>(R.layout.find_content_we_chat_fragment), RvScrollToTop,SelectPage{

    private var mFragmentInit = false
    private var isTabLayoutClick = false

    override fun getViewModel(app: Application): FindContentWeChatViewModel = FindContentWeChatViewModel(app)

    override fun pageIndex(): Int = 2

    override fun onViewInit() {
        super.onViewInit()
        mFragmentInit = true
        bindScrollListener()
    }

    override fun onEvent() {
        super.onEvent()
        if (isTabLayoutClick) {
            onSelectPage()
        }
        register()
    }

    private val mObserver = Observer<CollectChangeBean> {
        mRealVM.updateCollectState(it)
    }

    override fun onResume() {
        super.onResume()
        GlobalSingle.onCollectChange.observe(this, mObserver)
    }

    override fun onPause() {
        super.onPause()
        GlobalSingle.onCollectChange.removeObserver(mObserver)
    }


    override fun bindScrollListener() {
        RvScrollDelegate.bindScrollListener(mainActivity = mActivity as MainActivity, rvVM = mRealVM.rvVMRight)
    }

    override fun scrollToTop() {
        RvScrollDelegate.scrollToTop(mRealVM.rvVMRight)
    }

    override fun onSelectPage() {
        if (!mFragmentInit) {
            isTabLayoutClick = true
        } else {
            if (!mRealVM.isRequestSuccess) {
                mRealVM.requestServer()
            }
        }
    }

}