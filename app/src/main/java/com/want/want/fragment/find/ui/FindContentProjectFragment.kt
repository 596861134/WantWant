package com.want.want.fragment.find.ui

import android.app.Application
import androidx.lifecycle.Observer
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.activity.main.MainActivity
import com.want.want.bean.CollectChangeBean
import com.want.want.databinding.FindContentProjectFragmentBinding
import com.want.want.fragment.find.viewmodel.FindContentProjectViewModel
import com.want.want.rv.RvScrollToTop
import com.want.want.utils.GlobalSingle
import com.want.want.utils.RvScrollDelegate
import com.want.want.utils.SelectPage

class FindContentProjectFragment
    : BaseVMRepositoryFragment<FindContentProjectViewModel,FindContentProjectFragmentBinding>(R.layout.find_content_project_fragment)
    ,RvScrollToTop,SelectPage {

    private var mFragmentInit = false
    private var isTabLayoutClick = false

    override fun getViewModel(app: Application): FindContentProjectViewModel = FindContentProjectViewModel(app)

    override fun onViewInit() {
        super.onViewInit()
        mFragmentInit = true
        bindScrollListener()
    }

    override fun onEvent() {
        super.onEvent()
        if (isTabLayoutClick){
            onSelectPage()
        }
        register()
    }

    override fun bindScrollListener() {
        RvScrollDelegate.bindScrollListener(mainActivity = mActivity as MainActivity, rvVM = mRealVM.rvVM)
    }

    override fun scrollToTop() {
        RvScrollDelegate.scrollToTop(mRealVM.rvVM)
    }

    override fun pageIndex() = 3

    override fun onSelectPage() {
        if (!mFragmentInit) {
            isTabLayoutClick = true
        } else {
            if (!mRealVM.isRequestSuccess) {
                mRealVM.requestServer()
            }
        }
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



}