package com.want.want.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by chengzf on 2021/5/13.
 * fragment+viewpager 适配器
 */
class FragmentAdapter(private val mData:List<Fragment>, fragmentManager: FragmentManager, lifecycle: Lifecycle)
    :FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount(): Int = mData.size

    override fun createFragment(position: Int): Fragment = mData[position]
}