package com.want.want.activity.setting

import com.want.common.utils.MMKVUtil
import com.want.network.util.response
import com.want.want.network.NetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by chengzf on 2021/6/1.
 */
class SettingRepository :NetRepository() {

    /**
     * 退出登录
     */
    suspend fun userLogout() = withContext(Dispatchers.IO){
        var result = false
        response(api.userLogout()){
            MMKVUtil.clearAll()
            result = true
        }
        result
    }
}