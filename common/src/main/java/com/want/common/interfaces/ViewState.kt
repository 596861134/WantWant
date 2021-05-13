package com.want.common.interfaces

import androidx.lifecycle.Observer
import com.want.common.view.BaseActivity
import com.want.common.viewmodel.BaseViewModel

/**
 * @author czf
 * @date 2020/10/22
 */
interface ViewState {

    fun beforeSetView()

    fun onViewInit()

    fun onEvent()

    fun BaseViewModel.dialogState(baseActivity: BaseActivity) {
        isDialogShow.observe(baseActivity, Observer {
            if (it) baseActivity.showDialog() else baseActivity.dismissDialog()
        })
    }

    fun BaseViewModel.finish(baseActivity: BaseActivity) {
        isFinish.observe(baseActivity, Observer {
            if (it) {
                baseActivity.finish()
                isFinish.value = false
            }
        })
    }

}