package com.want.want.fragment.find.repository

import com.want.want.network.NetRepository

/**
 * Created by chengzf on 2021/6/3.
 */
class FindContentProjectTreeRepository:NetRepository() {

    suspend fun projectTreeList() = api.projectTreeList()

    suspend fun projectListCid(page: Int, cid: Int?) = api.projectListCid(page,cid)

}