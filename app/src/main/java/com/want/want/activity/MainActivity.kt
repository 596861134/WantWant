package com.want.want.activity

import com.want.common.view.BaseViewModelActivity
import com.want.want.R
import com.want.want.viewmodel.MainViewModel

class MainActivity : BaseViewModelActivity<MainViewModel>(R.layout.activity_main, MainViewModel::class.java) {

    override fun onViewInit() {
        super.onViewInit()

    }

}