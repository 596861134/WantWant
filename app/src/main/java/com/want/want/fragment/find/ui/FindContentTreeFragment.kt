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

class FindContentTreeFragment : BaseVMRepositoryFragment<FindContentTreeViewModel,FindContentTreeFragmentBinding>(R.layout.find_content_tree_fragment) {

    override fun getViewModel(app: Application): FindContentTreeViewModel = FindContentTreeViewModel(app)


}