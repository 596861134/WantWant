package com.want.want.bean

import com.want.network.BaseBean

/**
 * Created by chengzf on 2021/6/3.
 */
data class WeChatListBean(
    val data: List<Data?>? = null
):BaseBean(){
    data class Data(
        val children: List<Any?>? = null,
        val courseId: Int? = null,
        val id: Int? = null,
        val name: String? = null,
        val order: Int? = null,
        val parentChapterId: Int? = null,
        val userControlSetTop: Boolean? = null,
        val visible: Int? = null
    )
}
