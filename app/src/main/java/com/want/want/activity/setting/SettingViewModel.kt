package com.want.want.activity.setting

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.launch
import com.want.network.util.logoutHint
import com.want.want.AppApplication
import com.want.want.BuildConfig
import com.want.want.utils.GlobalSingle
import com.want.want.viewmodel.TitleViewModel

/**
 * Created by chengzf on 2021/6/1.
 */
class SettingViewModel(app:Application):BaseRepositoryViewModel<SettingRepository>(app, SettingRepository()) {

    var mTitleVM = TitleViewModel(
        leftAction = {
            finish()
        },
        title = "设置"
    )

    var mVersionName = ObservableField("WantWant\t\tVersion\t${BuildConfig.VERSION_NAME}")
    var onLogoutClick = MutableLiveData<Boolean>()
    var isLogin = ObservableField(AppApplication.isLogin)

    fun onLogout(){
        onLogoutClick.value = true
    }

    fun logout(){
        launch {
            if (mRepo.userLogout()){
                AppApplication.isLogin = false
                AppApplication.nikeName = null
                AppApplication.userId = null
                isLogin.set(false)
                GlobalSingle.isLoginSuccess.value = false
                logoutHint()
                finish()
            }
        }
    }
}