package com.want.want.network

import com.want.common.interfaces.BaseRepository
import com.want.network.WantService

/**
 * Created by chengzf on 2021/5/13.
 */
open class NetRepository: BaseRepository {

    val api by lazy { WantService.create<ApiService>() }

}