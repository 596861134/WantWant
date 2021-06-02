package com.want.want.fragment.collect

import com.want.want.bean.UserPrivateArticles
import com.want.want.network.NetRepository

/**
 * Created by chengzf on 2021/6/1.
 */
class CollectContentRepository :NetRepository() {

    /**
     * 收藏文章列表
     */
    suspend fun collectArticle(page:Int) = api.lgCollectList(page)

    /**
     * 面试文章列表
     */
    suspend fun interviewRelate(page: Int) = api.articleList(page,73)

    /**
     * 分享的文章列表
     */
    suspend fun shareArticle(page: Int):UserPrivateArticles{
        var tempPage = page
        return api.userLgPrivateArticles(++tempPage)
    }

    /**
     * 收藏的网站列表
     */
    suspend fun collectWebsite() = api.lgCollectWebsiteList()

    /**
     * 删除收藏的网站
     */
    suspend fun delCollectWebsite(id:Int) = api.delCollectWebsite(id)

    /**
     * 删除收藏的文章
     */
    suspend fun delMyArticle(id:Int?) = api.delMyArticle(id)

}