package com.want.want.common

import java.io.Serializable

/**
 * @author jhb
 * @date 2020/11/13
 */
data class EditDialogEventBean(var id: Int?, var name: String?, var link: String?)

data class EditDialogEvent(var page: EditPage, var bean: EditDialogEventBean? = null, var collectContentPage: CollectContentPage)

data class CommonItemBean(var id: Int?, var title: String?, var link: String?, var collect: Boolean) : Serializable

data class CollectChangeBean(var isCollect: Boolean, var id: Int)

