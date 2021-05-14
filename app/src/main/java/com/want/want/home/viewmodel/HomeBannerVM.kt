package com.want.want.home.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.want.common.ItemType
import com.want.common.rv.BaseMultiItemViewModel
import com.want.want.bean.BannerViewModel

/**
 * Created by chengzf on 2021/5/14.
 */
class HomeBannerVM(app:Application) :BaseMultiItemViewModel(app) {

    override val itemType: Int = ItemType.ITEM_HOME_BANNER

    var mBannerVM = ObservableField<BannerViewModel>()

    var mContent = ObservableField("Banner")

}