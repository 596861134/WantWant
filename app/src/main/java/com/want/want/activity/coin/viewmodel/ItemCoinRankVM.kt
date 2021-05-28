package com.want.want.activity.coin.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.want.common.rv.BaseItemViewModel

/**
 * Created by chengzf on 2021/5/28.
 */
class ItemCoinRankVM(app:Application):BaseItemViewModel(app) {

    var mRank = ObservableField("")
    var mName = ObservableField("")
    var mCount = ObservableField("")
}