package com.want.common.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by chengzf on 2021/5/12.
 */
open class BaseViewModelFactory(app:Application, private val viewModel: BaseRepositoryViewModel<*>)
    :ViewModelProvider.AndroidViewModelFactory(app) {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}