package com.want.want.fragment.find.repository

import com.want.want.network.NetRepository

/**
 * Created by chengzf on 2021/6/3.
 */
class FindContentTreeRepository: NetRepository() {

    suspend fun treeList() = api.treeList()
}