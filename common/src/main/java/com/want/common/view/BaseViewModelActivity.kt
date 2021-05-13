package com.want.common.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.want.common.interfaces.ViewState
import com.want.common.viewmodel.BaseLayoutViewModel

/**
 * Created by chengzf on 2021/5/13.
 */
open class BaseViewModelActivity<VM:BaseLayoutViewModel>(@LayoutRes private val layoutId:Int,private val clazz:Class<VM>):BaseActivity(),ViewState {

    lateinit var mRealVM:VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetView()
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, layoutId)
        mRealVM = ViewModelProvider(this)[clazz]
        binding.setVariable(mRealVM.id(), mRealVM)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        onViewInit()
        mRealVM.setBundle(intent.extras ?: Bundle())
        mRealVM.onModelBind()
        onEvent()
    }

    override fun beforeSetView() {
    }

    override fun onViewInit() {
    }

    override fun onEvent() {
        mRealVM.dialogState(this)
        mRealVM.finish(this)
    }
}