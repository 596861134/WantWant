package com.want.want.network

import com.want.want.bean.ArrayDataBean
import com.want.want.bean.BannerDataBean
import com.want.want.bean.ObjectDataBean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by chengzf on 2021/5/13.
 */
interface ApiService {

    //首页banner
    @GET("/banner/json")
    suspend fun banner(): BannerDataBean

    //置顶文章
    @GET("/article/top/json")
    suspend fun articleTop(): ArrayDataBean

    /**
     *  1.首页文章列表
     *  2.知识体系下的文章  cid 分类的id，上述二级目录的id
     */
    @GET("/article/list/{page}/json")
    suspend fun articleList(
        @Path("page") page: Int,
        @Query("cid") cid: Int? = null//73为面试的cid
    ): ObjectDataBean

    //问答
    @GET("wenda/list/{page}/json ")
    suspend fun wendaList(@Path("page") page: Int): ObjectDataBean

    //自己收藏文章列表
    @GET("lg/collect/list/{page}/json")
    suspend fun lgCollectList(@Path("page") page: Int): ObjectDataBean
}