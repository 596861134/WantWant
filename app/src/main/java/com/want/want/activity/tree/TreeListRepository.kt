package com.want.want.activity.tree

import com.want.want.network.NetRepository

/**
 * Created by chengzf on 2021/6/4.
 */
class TreeListRepository:NetRepository() {

    suspend fun articleList(page: Int, cid: Int?) = api.articleList(page, cid)
}