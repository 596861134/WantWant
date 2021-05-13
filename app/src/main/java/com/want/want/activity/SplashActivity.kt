package com.want.want.activity

import com.want.common.utils.delay
import com.want.common.view.BaseViewModelActivity
import com.want.want.R
import com.want.want.viewmodel.SplashViewModel

class SplashActivity : BaseViewModelActivity<SplashViewModel>(R.layout.activity_splash,SplashViewModel::class.java) {

    override fun onViewInit() {
        super.onViewInit()
        800.delay {
            startActivity(MainActivity::class.java)
            finish()
        }
    }
}