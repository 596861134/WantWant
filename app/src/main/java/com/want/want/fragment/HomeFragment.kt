package com.want.want.fragment

import android.app.Application
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.databinding.HomeFragmentBinding
import com.want.want.home.viewmodel.HomeViewModel

class HomeFragment : BaseVMRepositoryFragment<HomeViewModel,HomeFragmentBinding>(R.layout.home_fragment) {

    override fun getViewModel(app: Application): HomeViewModel {
        return HomeViewModel(app)
    }
}