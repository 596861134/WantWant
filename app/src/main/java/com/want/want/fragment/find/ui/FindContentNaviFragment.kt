package com.want.want.fragment.find.ui

import android.app.Application
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.databinding.FindContentNaviFragmentBinding
import com.want.want.fragment.find.viewmodel.FindContentNaviViewModel

class FindContentNaviFragment : BaseVMRepositoryFragment<FindContentNaviViewModel,FindContentNaviFragmentBinding>(R.layout.find_content_navi_fragment) {

    override fun getViewModel(app: Application): FindContentNaviViewModel = FindContentNaviViewModel(app)


}