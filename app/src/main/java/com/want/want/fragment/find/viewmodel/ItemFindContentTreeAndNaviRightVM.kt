package com.want.want.fragment.find.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.want.common.rv.BaseItemViewModel
import com.want.common.utils.getRandomColor
import com.want.common.utils.showToast
import com.want.want.activity.X5WebActivity
import com.want.want.common.CommonItemBean
import com.want.want.viewmodel.X5WebViewModel

/**
 * Created by chengzf on 2021/6/3.
 */
class ItemFindContentTreeAndNaviRightVM(app:Application, private val isNavi: Boolean = false) :BaseItemViewModel(app) {
    var mBgColor = ObservableField(getRandomColor())
    var mContent = ObservableField<String>()
    var mCid:Int? = 0
    var mLink:String? = null

    override fun onItemClick() {
        if (isNavi){
            startActivity(X5WebActivity::class.java,
                X5WebViewModel.FLAG_BEAN to CommonItemBean(null,mContent.get(),mLink,false),
                X5WebViewModel.FLAG_SHOW_COLLECT_ICON to false
            )
        }else{
            // todo TreeListActivity
            "TreeListActivity".showToast()
        }
    }

}