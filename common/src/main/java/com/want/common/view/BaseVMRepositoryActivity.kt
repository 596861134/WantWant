package com.want.common.view

import android.app.Application
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.want.common.interfaces.ViewState
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.common.viewmodel.BaseViewModelFactory

/**
 * Created by chengzf on 2021/5/13.
 */
abstract class BaseVMRepositoryActivity<VM: BaseRepositoryViewModel<*>>(@LayoutRes private val layoutId:Int):BaseActivity(), ViewState {

    lateinit var mRealVM:VM

    abstract fun getViewModel(app: Application): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetView()
        val vm = getViewModel(application)
        mRealVM = ViewModelProvider(this,BaseViewModelFactory(application,vm))[vm::class.java]
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this,layoutId)
        binding.lifecycleOwner = this
        binding.setVariable(mRealVM.id(),mRealVM)
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