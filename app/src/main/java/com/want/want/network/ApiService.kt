package com.want.want.network

import com.want.want.bean.BannerDataBean
import retrofit2.http.GET

/**
 * Created by chengzf on 2021/5/13.
 */
interface ApiService {

    //首页banner
    @GET("/banner/json")
    suspend fun banner(): BannerDataBean
}