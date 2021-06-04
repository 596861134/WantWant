package com.want.want.fragment.find.viewmodel

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.want.common.rv.BaseItemViewModel
import com.want.want.activity.X5WebActivity
import com.want.want.common.CommonItemBean
import com.want.want.viewmodel.X5WebViewModel

/**
 * Created by chengzf on 2021/6/4.
 */
class ItemFindContentProjectVM(app:Application):BaseItemViewModel(app) {
    var mPath = ObservableField<String>()
    var mTitle = ObservableField<String>()
    var mTime = ObservableField<String>()
    var mDesc = ObservableField<String>()
    var mAuthor = ObservableField<String>()
    var mChecked = ObservableBoolean(false)
    var mCollect = ObservableBoolean()
    var mId:Int? = 0
    var mLink:String? = null
    var onCollectClick = {}
    var onClickItem = {}

    override fun onItemClick() {
        startActivity(
            X5WebActivity::class.java,
            X5WebViewModel.FLAG_BEAN to CommonItemBean(mId, mTitle.get(), mLink, false)
        )
    }

}