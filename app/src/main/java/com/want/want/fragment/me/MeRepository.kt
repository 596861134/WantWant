package com.want.want.fragment.me

import com.want.network.util.response
import com.want.want.AppApplication
import com.want.want.bean.CoinUserInfoBean
import com.want.want.network.NetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by chengzf on 2021/5/27.
 */
class MeRepository:NetRepository() {

    suspend fun coinUserInfo() = withContext(Dispatchers.IO){
        var coinUserInfo:CoinUserInfoBean.Data? = null
        response(api.coinUserInfo()){
            coinUserInfo = data
            coinUserInfo?.mNikeName = AppApplication.nikeName
        }
        coinUserInfo
    }

}