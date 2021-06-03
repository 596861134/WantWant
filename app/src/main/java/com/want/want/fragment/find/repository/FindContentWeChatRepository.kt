package com.want.want.fragment.find.repository

import com.want.want.network.NetRepository

/**
 * Created by chengzf on 2021/6/3.
 */
class FindContentWeChatRepository:NetRepository() {

    suspend fun weChatList() = api.weChatList()

    suspend fun weChatListDetail(id: Int?, page: Int) = api.weChatListDetail(id, page)
}