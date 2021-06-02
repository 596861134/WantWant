package com.want.want.fragment.collect

import android.app.Application
import androidx.databinding.ObservableField
import com.want.common.ItemType
import com.want.common.rv.BaseMultiItemViewModel
import com.want.want.activity.X5WebActivity
import com.want.want.common.CommonItemBean
import com.want.want.viewmodel.X5WebViewModel

/**
 * Created by chengzf on 2021/6/1.
 */
class ItemCollectWebsiteVM(app: Application) : BaseMultiItemViewModel(app) {

    override val itemType: Int = ItemType.ITEM_COLLECT_WEBSITE

    var mTitle = ObservableField("")
    var mLink = ObservableField("")

    var mId: Int? = null

    var onEdit = {}

    var onDel = {}

    override fun onItemClick() {
        super.onItemClick()
        startActivity(
            X5WebActivity::class.java,
            X5WebViewModel.FLAG_BEAN to CommonItemBean(mId, mTitle.get(), mLink.get(), false),
            X5WebViewModel.FLAG_SHOW_COLLECT_ICON to false
        )
    }
}