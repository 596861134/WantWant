package com.want.common.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.want.common.interfaces.VariableId

/**
 * Created by chengzf on 2021/5/13.
 */
class QuickViewHolder(private val mBinding:ViewDataBinding):BaseViewHolder(mBinding.root) {
    companion object{
        fun create(@LayoutRes layoutId:Int, parent:ViewGroup): QuickViewHolder =
            QuickViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),layoutId,parent,false))
    }

    fun <T:VariableId> bind(item:T){
        mBinding.setVariable(item.id(),item)
        mBinding.executePendingBindings()
    }
}