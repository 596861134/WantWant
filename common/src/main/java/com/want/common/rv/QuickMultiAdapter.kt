package com.want.common.rv

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.want.common.interfaces.VariableId

/**
 * Created by chengzf on 2021/5/13.
 */
class QuickMultiAdapter<T:BaseMultiItemViewModel>(mData:MutableList<T>)
    :BaseMultiItemQuickAdapter<T,QuickViewHolder>(mData) {

    override fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): QuickViewHolder {
        return QuickViewHolder.create(layoutResId,parent)
    }

    override fun convert(holder: QuickViewHolder, item: T) {
        holder.bind(item)
    }

    fun addType(@LayoutRes layoutResId: Int,type:Int){
        addItemType(type,layoutResId)
    }

    fun addHeader(context: Context, @LayoutRes layoutId: Int, header: VariableId) {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(context), layoutId, null, false)
        binding.setVariable(header.id(), header)
        binding.executePendingBindings()
        addHeaderView(binding.root)
    }

}