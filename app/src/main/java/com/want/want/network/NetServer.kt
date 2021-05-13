package com.want.want.network

import com.want.network.WantService

/**
 * Created by chengzf on 2021/5/13.
 */
object NetServer {

    val api by lazy { WantService.create<ApiService>() }

}