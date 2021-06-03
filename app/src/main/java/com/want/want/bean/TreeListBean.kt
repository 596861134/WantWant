package com.want.want.bean

import com.want.network.BaseBean

/**
 * Created by chengzf on 2021/6/3.
 */
data class TreeListBean(
    var data:List<DataBean>? = null
):BaseBean(){

    data class DataBean(
        var children: List<ChildrenBean?>?,
        var courseId: Int?,
        var id: Int?,
        var name: String?,
        var order: Int?,
        var parentChapterId: Int?,
        var userControlSetTop: Boolean?,
        var visible: Int?
    ){
        data class ChildrenBean(
            var children: List<Any?>?,
            var courseId: Int?,
            var id: Int?,
            var name: String?,
            var order: Int?,
            var parentChapterId: Int?,
            var userControlSetTop: Boolean?,
            var visible: Int?
        )
    }
}
