package com.want.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.want.common.interfaces.ViewState
import com.want.common.viewmodel.BaseLayoutViewModel

/**
 * Created by chengzf on 2021/5/13.
 */
open class BaseViewModelFragment<VM:BaseLayoutViewModel>(@LayoutRes private val layoutId:Int, private val clazz:Class<VM>):BaseFragment(), ViewState {
    lateinit var mRealVM: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        beforeSetView()
        mRealVM = ViewModelProvider(this)[clazz]
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater,layoutId,null,false)
        binding.lifecycleOwner = this
        binding.setVariable(mRealVM.id(),mRealVM)
        binding.executePendingBindings()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewInit()
        mRealVM.onModeBind()
        onEvent()
    }

    override fun beforeSetView() {
    }

    override fun onViewInit() {
    }

    override fun onEvent() {
        mRealVM.dialogState(mActivity)
        mRealVM.finish(mActivity)
    }
}