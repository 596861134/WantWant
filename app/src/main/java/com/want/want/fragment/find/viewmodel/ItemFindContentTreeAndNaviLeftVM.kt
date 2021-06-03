package com.want.want.fragment.find.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.want.common.rv.BaseItemViewModel
import com.want.common.utils.getResColor
import com.want.want.R

/**
 * Created by chengzf on 2021/6/3.
 */
class ItemFindContentTreeAndNaviLeftVM(app:Application):BaseItemViewModel(app) {
    var mBgColor = ObservableField(R.color.colorWhiteDark.getResColor())
    var mContent = ObservableField<String>()
    var mCid:Int? = 0
    var mChecked = ObservableField<Boolean>(false)
    var onClickItem = {}
}