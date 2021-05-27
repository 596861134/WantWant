package com.want.want.bean

import com.want.network.BaseBean
import java.io.Serializable

/**
 * Created by chengzf on 2021/5/27.
 */
data class CoinUserInfoBean(
    val data:Data? = null
):BaseBean(), Serializable {

    data class Data(
        val coinCount: Int? = null,
        val level: Int? = null,
        val rank: String? = null,
        val userId: Int? = null,
        val username: String? = null,
        var mNikeName: String? = null,
        var mLastTime: Long = System.currentTimeMillis()
    ) : Serializable
}