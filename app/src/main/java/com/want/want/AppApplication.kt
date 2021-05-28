package com.want.want

import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.QbSdk.PreInitCallback
import com.want.common.BaseApplication
import com.want.common.CommonUtil
import com.want.common.Constant
import com.want.common.utils.MMKVUtil
import com.want.common.utils.logWithTag


/**
 * Created by chengzf on 2021/4/9.
 */
class AppApplication : BaseApplication() {

    companion object{
        var isLogin = false
        var userId:Int? = null
        var nikeName:String? = null
    }

    override fun onCreate() {
        super.onCreate()
        initSdk()
        initUserInfo()
    }

    private fun initUserInfo() {
        isLogin = MMKVUtil.decodeBoolean(Constant.IS_LOGIN)
        userId = MMKVUtil.decodeInt(Constant.USER_ID)
        nikeName = MMKVUtil.decodeString(Constant.USER_NIKE_NAME)
    }

    private fun initSdk() {
        val cb: PreInitCallback = object : PreInitCallback {
            override fun onViewInitFinished(arg0: Boolean) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                "X5 浏览器内核初始化完毕".logWithTag(CommonUtil.TAG)
            }

            override fun onCoreInitFinished() {
            }
        }
        //x5内核初始化接口
        QbSdk.initX5Environment(applicationContext, cb)
    }
}