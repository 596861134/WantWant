package com.want.want.viewmodel

import androidx.databinding.ObservableField
import com.want.common.utils.getRandomColor
import com.want.common.utils.getResColor
import com.want.common.utils.getResDimen
import com.want.common.utils.getResDrawable
import com.want.want.R

/**
 * @author jhb
 * @date 2020/10/23
 */
class TagViewModel {
    var mBgColor = ObservableField(getRandomColor())
    var mContent = ObservableField("")
    var mTextColor = ObservableField(R.color.colorRed.getResColor())
    var mTextSize = ObservableField(R.dimen.sp_12.getResDimen())
    var mDrawable = ObservableField(R.drawable.rect_red_shape.getResDrawable())
}