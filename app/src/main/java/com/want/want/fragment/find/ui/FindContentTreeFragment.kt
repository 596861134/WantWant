package com.want.want.fragment.find.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.databinding.FindContentTreeFragmentBinding
import com.want.want.fragment.find.viewmodel.FindContentTreeViewModel
import com.want.want.utils.SelectPage

class FindContentTreeFragment :
    BaseVMRepositoryFragment<FindContentTreeViewModel, FindContentTreeFragmentBinding>(R.layout.find_content_tree_fragment),SelectPage {

    private var mFragmentInit = false
    private var isTabLayoutClick = false

    override fun getViewModel(app: Application): FindContentTreeViewModel = FindContentTreeViewModel(app)

    override fun onViewInit() {
        super.onViewInit()
        mFragmentInit = true
    }

    override fun onEvent() {
        super.onEvent()
        if (isTabLayoutClick){
            onSelectPage()
        }
        register()
    }

    override fun pageIndex(): Int = 0

    override fun onSelectPage() {
        if (!mFragmentInit){
            isTabLayoutClick = true
        }else{
            if (!mRealVM.isRequestSuccess){
                mRealVM.requestServer()
            }
        }

    }


}