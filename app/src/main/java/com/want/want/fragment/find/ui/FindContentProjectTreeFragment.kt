package com.want.want.fragment.find.ui

import android.app.Application
import androidx.lifecycle.Observer
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.activity.main.MainActivity
import com.want.want.bean.CollectChangeBean
import com.want.want.databinding.FindContentProjectTreeFragmentBinding
import com.want.want.fragment.find.viewmodel.FindContentProjectTreeViewModel
import com.want.want.rv.RvScrollToTop
import com.want.want.utils.GlobalSingle
import com.want.want.utils.RvScrollDelegate
import com.want.want.utils.SelectPage

class FindContentProjectTreeFragment
    : BaseVMRepositoryFragment<FindContentProjectTreeViewModel,FindContentProjectTreeFragmentBinding>(R.layout.find_content_project_tree_fragment),
    RvScrollToTop,SelectPage{

    private var mFragmentInit = false
    private var isTabLayoutClick = false

    override fun getViewModel(app: Application): FindContentProjectTreeViewModel = FindContentProjectTreeViewModel(app)

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
        RvScrollDelegate.bindScrollListener(mainActivity = mActivity as MainActivity, rvVM = mRealVM.rightVM)
    }

    override fun scrollToTop() {
        RvScrollDelegate.scrollToTop(mRealVM.rightVM)
    }

    override fun pageIndex() = 4

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