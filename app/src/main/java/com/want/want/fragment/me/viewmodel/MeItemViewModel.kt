package com.want.want.fragment.me.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.want.common.ItemType
import com.want.common.rv.BaseMultiItemViewModel
import com.want.common.utils.getResDrawable
import com.want.want.R

/**
 * Created by chengzf on 2021/5/27.
 */
class MeItemViewModel(app:Application):BaseMultiItemViewModel(app) {

    override val itemType: Int = ItemType.ITEM_ME_ITEM

    var onClick = {}

    var mContent = ObservableField("")
    var mCoinCount = ObservableField("")
    var mIcon = ObservableField(R.mipmap.jifen_ico.getResDrawable())
    var mShowDivider = ObservableField(true)
    var mShowMargin = ObservableField(true)

}