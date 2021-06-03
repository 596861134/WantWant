package com.want.want.fragment.find.ui

import android.app.Application
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.databinding.FindContentNaviFragmentBinding
import com.want.want.fragment.find.viewmodel.FindContentNaviViewModel
import com.want.want.rv.RvScrollToTop
import com.want.want.utils.SelectPage

class FindContentNaviFragment
    : BaseVMRepositoryFragment<FindContentNaviViewModel,FindContentNaviFragmentBinding>(R.layout.find_content_navi_fragment),
    RvScrollToTop,SelectPage {

    private var mFragmentInit = false
    private var isTabLayoutClick = false

    override fun getViewModel(app: Application): FindContentNaviViewModel = FindContentNaviViewModel(app)

    override fun onViewInit() {
        super.onViewInit()
        mFragmentInit = true
    }

    override fun onEvent() {
        super.onEvent()
        if (isTabLayoutClick){
            onSelectPage()
        }
        register()
    }

    override fun pageIndex(): Int = 1

    override fun bindScrollListener() {
    }

    override fun scrollToTop() {
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