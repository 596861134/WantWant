package com.want.common.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ScreenUtils
import com.want.common.viewmodel.BaseLayoutViewModel

/**
 * Created by chengzf on 2021/5/13.
 */
open class BaseDialog<VM: BaseLayoutViewModel, T:ViewDataBinding>(
    private val layoutId:Int, val vm:Class<VM>, private val activity:AppCompatActivity, theme: Int)
    :AppCompatDialog(activity,theme) {

    lateinit var mRealVM:VM
    lateinit var mBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRealVM = ViewModelProvider(activity,ViewModelProvider.AndroidViewModelFactory(activity.application))[vm]

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), layoutId, null, false)
        setContentView(mBinding.root)
        mBinding.setVariable(mRealVM.id(),mRealVM)
        mBinding.executePendingBindings()

        setCancelable(false)
        setCanceledOnTouchOutside(true)

        val window = window
        val attributes = window?.attributes
        attributes?.width = ScreenUtils.getScreenWidth()
        attributes?.height = ScreenUtils.getScreenHeight() / 5 * 3
        window?.attributes = attributes

        initView()
        mRealVM.onModelBind()
        initData()
    }

    open fun initView() {

    }

    open fun initData() {

    }


}