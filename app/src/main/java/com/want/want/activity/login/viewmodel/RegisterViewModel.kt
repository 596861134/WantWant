package com.want.want.activity.login.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.want.common.Constant
import com.want.common.utils.MMKVUtil
import com.want.common.utils.logWithTag
import com.want.common.utils.showToast
import com.want.common.viewmodel.BaseLayoutViewModel
import com.want.network.NetConstant
import com.want.network.util.launch
import com.want.want.AppApplication
import com.want.want.R
import com.want.want.common.CollectContentPage
import com.want.want.network.NetRepository
import com.want.want.utils.GlobalSingle
import com.want.want.viewmodel.TitleViewModel

/**
 * Created by chengzf on 2021/5/27.
 */
class RegisterViewModel(app:Application):BaseLayoutViewModel(app) {

    companion object {
        const val COLLECT_CONTENT_PAGE = "collect_content_page"
    }

    var mUserName = ObservableField<String>()
    var mPassword = ObservableField<String>()
    var mRePassword = ObservableField<String>()

    val mTitleVM = TitleViewModel(
        leftAction = {
            GlobalSingle.isLoginSuccess.value = false
            finish()
        },
        title = app.getString(R.string.text_register)
    )

    private var mPage: CollectContentPage? = null

    override fun onModelBind() {
        super.onModelBind()
        mPage = mBundle.getSerializable(COLLECT_CONTENT_PAGE) as? CollectContentPage
        "mPage = $mPage".logWithTag("CollectContent")
    }

    fun onRegister(){
        if (mUserName.get().isNullOrEmpty()){
            "请输入账号".showToast()
            return
        }

        if (mPassword.get().isNullOrEmpty()){
            "请输入密码".showToast()
            return
        }

        if (mRePassword.get().isNullOrEmpty()){
            "请输入确认密码".showToast()
            return
        }

        if (mPassword.get() != mRePassword.get()){
            "两次密码输入不一致".showToast()
            return
        }

        launch {
            NetRepository().api.userRegister(mUserName.get(),mPassword.get(),mRePassword.get())?.apply {
                val body = body()
                when(body?.errorCode){
                    NetConstant.SUCCESS -> {
                        val cookieSet = HashSet<String>()
                        headers().forEach { header->
                            if (header.first == "Set-Cookie") {
                                cookieSet.add(header.second)
                            }
                        }

                        MMKVUtil.encodeSet(Constant.KEY_COOKIE,cookieSet)
                        MMKVUtil.encode(Constant.USER_NIKE_NAME, body.data?.nickname ?: body.data?.publicName ?: body.data?.username ?: "")
                        MMKVUtil.encode(Constant.IS_LOGIN,true)
                        MMKVUtil.encode(Constant.USER_ID,body.data?.id)
//                        AppApplication.isLogin = true
                        GlobalSingle.isLoginSuccess.value = true
                        mPage?.let {
                            GlobalSingle.isLoginSuccessToCollect.value = it
                        }
                        finish()
                    }
                    else -> "${body?.errorMsg}".showToast()
                }
            }
        }
    }
}