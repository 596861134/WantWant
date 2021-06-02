package com.want.want.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * Created by chengzf on 2021/6/1.
 */
interface SelectPage {

    fun pageIndex():Int = -1

    fun onSelectPage()

    fun LifecycleOwner.register(){
        GlobalSingle.onFindPageSelect.observe(this, Observer {
            if (it == pageIndex()){
                onSelectPage()
                GlobalSingle.onFindPageSelect.value = -1
            }

        })
    }
}