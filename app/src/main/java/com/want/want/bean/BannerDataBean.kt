package com.want.want.bean

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.want.network.BaseBean

/**
 * Created by chengzf on 2021/5/13.
 */

@Entity(tableName = "BannerDataBean")
data class BannerDataBean(
    @PrimaryKey
    var index: Int,
    var data: List<Data>?,
    var mLastTime: Long = System.currentTimeMillis()
) : BaseBean() {

    data class Data(
        var desc: String?,
        var id: Int?,
        var isVisible: Int?,
        var order: Int?,
        var type: Int?,
        var imagePath: String?,
        var title: String?,
        var url: String?
    )

}