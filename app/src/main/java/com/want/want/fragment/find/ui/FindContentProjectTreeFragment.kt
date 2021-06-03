package com.want.want.fragment.find.ui

import android.app.Application
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.databinding.FindContentProjectTreeFragmentBinding
import com.want.want.fragment.find.viewmodel.FindContentProjectTreeViewModel

class FindContentProjectTreeFragment : BaseVMRepositoryFragment<FindContentProjectTreeViewModel,FindContentProjectTreeFragmentBinding>(R.layout.find_content_project_tree_fragment) {

    override fun getViewModel(app: Application): FindContentProjectTreeViewModel = FindContentProjectTreeViewModel(app)

}