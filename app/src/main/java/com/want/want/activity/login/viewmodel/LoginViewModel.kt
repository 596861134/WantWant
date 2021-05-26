package com.want.want.activity.login.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.want.common.utils.logWithTag
import com.want.common.utils.showToast
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.launch
import com.want.want.AppApplication
import com.want.want.R
import com.want.want.activity.login.LoginRepository
import com.want.want.common.CollectContentPage
import com.want.want.utils.GlobalSingle
import com.want.want.viewmodel.TitleViewModel

/**
 * Created by chengzf on 2021/5/26.
 */
class LoginViewModel(app:Application):BaseRepositoryViewModel<LoginRepository>(app, LoginRepository()) {

    companion object {
        const val COLLECT_CONTENT_PAGE = "collect_content_page"
    }

    var mUserName = ObservableField<String>()
    var mPassword = ObservableField<String>()

    val mTitleVM = TitleViewModel(
        leftAction = {
            GlobalSingle.isLoginSuccess.value = false
            finish()
        },
        title = app.getString(R.string.text_login)
    )

    private var mPage: CollectContentPage? = null
    override fun onModelBind() {
        super.onModelBind()
        mPage = mBundle.getSerializable(COLLECT_CONTENT_PAGE) as? CollectContentPage
        "mPage = $mPage".logWithTag("CollectContent")
    }

    fun onLogin(){

        if (mUserName.get().isNullOrEmpty()){
            "请输入账号".showToast()
            return
        }

        if (mPassword.get().isNullOrEmpty()){
            "请输入密码".showToast()
            return
        }

        launch {
            if (mRepo.login(mUserName.get(),mPassword.get())){
                AppApplication.isLogin = true
                GlobalSingle.isLoginSuccess.value = true
                mPage?.let {
                    GlobalSingle.isLoginSuccessToCollect.value = it
                }
                finish()
            }
        }
    }

    fun onRegister(){

    }

}