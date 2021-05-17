package com.want.want.network

import com.want.common.interfaces.BaseRepository
import com.want.network.WantService

/**
 * Created by chengzf on 2021/5/13.
 */
open class NetRepository: BaseRepository {

    val api by lazy { WantService.create<ApiService>() }

    /**
     * 收藏
     */
    suspend fun collect(id:Int) = api.collect(id)

    /**
     * 取消收藏
     */
    suspend fun unCollect(id:Int,isOnMe:Boolean = false, originId:Int = -1) =
        if (isOnMe) api.unCollectInMe(id, originId) else api.unCollect(id)

}