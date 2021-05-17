package com.want.want.activity

import com.want.common.utils.delay
import com.want.common.view.BaseViewModelActivity
import com.want.want.R
import com.want.want.databinding.ActivitySplashBinding
import com.want.want.main.MainActivity
import com.want.want.viewmodel.CommonViewModel

class SplashActivity : BaseViewModelActivity<CommonViewModel, ActivitySplashBinding>(R.layout.activity_splash,CommonViewModel::class.java) {

    override fun onViewInit() {
        super.onViewInit()
        800.delay {
            startActivity(MainActivity::class.java)
            finish()
        }
    }
}