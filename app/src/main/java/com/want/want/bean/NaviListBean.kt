package com.want.want.bean

/**
 * Created by chengzf on 2021/6/3.
 */
data class NaviListBean(
    var data:List<Data>? = null
) {
    data class Data(
        var articles: List<ItemDatasBean>?,
        var name: String?,
        var cid: Int?
    )
}