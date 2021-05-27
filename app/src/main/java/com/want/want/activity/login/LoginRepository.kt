package com.want.want.activity.login

import com.want.common.Constant
import com.want.common.utils.MMKVUtil
import com.want.common.utils.showToast
import com.want.network.NetConstant
import com.want.want.AppApplication
import com.want.want.network.NetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by chengzf on 2021/5/26.
 */
class LoginRepository: NetRepository() {

    /**
     * 登录
     */
    suspend fun login(userName:String?,userPwd:String?) = withContext(Dispatchers.IO){
        var result:Boolean = false
        api.userLogin(userName,userPwd)?.apply {
            val body = body()
            when(body?.errorCode){
                NetConstant.SUCCESS -> {
                    val cookieSet = HashSet<String>()
                    headers().forEach { head ->
                        if (head.first == "Set-Cookie"){
                            cookieSet.add(head.second)
                        }
                    }
                    MMKVUtil.encodeSet(Constant.KEY_COOKIE,cookieSet)

                    val data = body.data
                    MMKVUtil.encode(Constant.USER_NIKE_NAME, data?.nickname ?: data?.publicName ?: data?.username ?: "")
                    AppApplication.nikeName = data?.nickname
                    data?.mIsLogin = true
                    data?.mLoginTime = System.currentTimeMillis()
                    result = true
                }

                else -> {
                    "${body?.errorMsg}".showToast()
                    result = false
                }
            }
        }
        result
    }
}