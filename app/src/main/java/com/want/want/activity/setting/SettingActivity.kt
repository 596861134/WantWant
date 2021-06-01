package com.want.want.activity.setting

import android.app.Application
import androidx.lifecycle.Observer
import com.want.common.view.BaseVMRepositoryActivity
import com.want.want.R
import com.want.want.databinding.ActivitySettingBinding
import com.want.want.utils.DialogUtil

class SettingActivity : BaseVMRepositoryActivity<SettingViewModel,ActivitySettingBinding>(R.layout.activity_setting) {

    override fun getViewModel(app: Application): SettingViewModel = SettingViewModel(app)

    override fun onEvent() {
        super.onEvent()
        mRealVM.onLogoutClick.observe(this, Observer { it ->
            if (it){
                mRealVM.onLogoutClick.value = false
                DialogUtil.showDialog(this,message = "你确定要退出吗？", positionAction = {
                    it.dismiss()
                    mRealVM.logout()
                })
            }
        })
    }

}