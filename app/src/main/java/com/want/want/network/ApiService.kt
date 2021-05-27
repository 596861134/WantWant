package com.want.want.network

import com.want.network.BaseBean
import com.want.want.bean.ArrayDataBean
import com.want.want.bean.BannerDataBean
import com.want.want.bean.ObjectDataBean
import com.want.want.bean.UserBean
import retrofit2.Response
import retrofit2.http.*

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

    //收藏站内文章 文章id，拼接在链接中。
    @POST("/lg/collect/{id}/json")
    suspend fun collect(@Path("id") id: Int?): BaseBean

    //取消收藏  文章列表 文章id，拼接在链接中。
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun unCollect(@Path("id") id: Int?): BaseBean

    /**
     * 取消收藏
     * 1.文章列表 id:拼接在链接上
     * 2.我的收藏页面（该页面包含自己录入的内容）id:拼接在链接上  originId:列表页下发，无则为-1
     */
    @FormUrlEncoded
    @POST("/lg/uncollect/{id}/json")
    suspend fun unCollectInMe(@Path("id") id: Int, @Field("originId") originId: Int): BaseBean

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("user/login")
    suspend fun userLogin(@Field("username") username: String?,
                          @Field("password") password: String?): Response<UserBean>?


    /**
     * 注册 username password repassword
     */
    @FormUrlEncoded
    @POST("/user/register")
    suspend fun userRegister(
        @Field("username") username: String?,
        @Field("password") password: String?,
        @Field("repassword") repassword: String?
    ): Response<UserBean>?

}