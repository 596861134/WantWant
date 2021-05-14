package com.want.want.viewmodel

import android.graphics.drawable.Drawable
import androidx.databinding.ObservableField
import com.want.common.utils.getDrawable
import com.want.common.utils.getResColor
import com.want.want.R

/**
 * Created by chengzf on 2021/5/14.
 */
data class TitleViewModel(var leftText:String = "",
                          var leftDrawable:Drawable? = R.drawable.abc_vector_test.getDrawable(),
                          var leftAction:(()->Unit)? = null,
                          var title: String = "",
                          var rightText: String = "",
                          var rightDrawable: Drawable? = null,
                          var rightAction: (() -> Unit)? = null,
                          var background: Int = R.color.colorAccent.getResColor()
) {
    val mTitle = ObservableField(title)
    val mRightDrawable = ObservableField(rightDrawable)
}