package com.want.want.activity.about

import android.text.method.LinkMovementMethod
import com.want.common.view.BaseViewModelActivity
import com.want.want.R
import com.want.want.databinding.ActivityAboutMeBinding

class AboutMeActivity : BaseViewModelActivity<AboutMeViewModel, ActivityAboutMeBinding>(
    R.layout.activity_about_me,AboutMeViewModel::class.java) {

    override fun onViewInit() {
        super.onViewInit()
        mBinding.tvContent.movementMethod = LinkMovementMethod.getInstance()
    }
}