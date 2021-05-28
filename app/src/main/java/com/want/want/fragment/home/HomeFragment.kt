package com.want.want.fragment.home

import android.app.Application
import androidx.lifecycle.Observer
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.bean.CollectChangeBean
import com.want.want.activity.main.MainActivity
import com.want.want.databinding.HomeFragmentBinding
import com.want.want.fragment.home.viewmodel.HomeViewModel
import com.want.want.rv.RvScrollToTop
import com.want.want.utils.GlobalSingle
import com.want.want.utils.RvScrollDelegate

class HomeFragment : BaseVMRepositoryFragment<HomeViewModel, HomeFragmentBinding>(R.layout.home_fragment),
    RvScrollToTop {

    override fun getViewModel(app: Application): HomeViewModel {
        return HomeViewModel(app)
    }

    override fun onViewInit() {
        super.onViewInit()
        bindScrollListener()
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
        RvScrollDelegate.bindScrollListener(mActivity as MainActivity, mRealVM.rvVM)
    }

    override fun scrollToTop() {
        RvScrollDelegate.scrollToTop(mRealVM.rvVM)
    }
}