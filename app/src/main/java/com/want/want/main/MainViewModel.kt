package com.want.want.main

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.want.common.viewmodel.BaseLayoutViewModel
import com.want.want.viewmodel.FabViewModel

/**
 * Created by chengzf on 2021/5/13.
 */
class MainViewModel(app:Application):BaseLayoutViewModel(app) {

    var mFabClick = MutableLiveData(false)
    var mFabVM = FabViewModel(
        onClick = {
            mFabClick.value = true
        }
    )
    var mFabVisible = ObservableField(false)


}