package com.want.want.fragment.find.ui

import android.app.Application
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.databinding.FindContentProjectFragmentBinding
import com.want.want.fragment.find.viewmodel.FindContentProjectViewModel

class FindContentProjectFragment : BaseVMRepositoryFragment<FindContentProjectViewModel,FindContentProjectFragmentBinding>(R.layout.find_content_project_fragment) {

    override fun getViewModel(app: Application): FindContentProjectViewModel = FindContentProjectViewModel(app)


}