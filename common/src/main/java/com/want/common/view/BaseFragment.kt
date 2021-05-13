package com.want.common.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * Created by chengzf on 2021/5/13.
 */
open class BaseFragment:Fragment() {

    lateinit var mActivity: BaseActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}