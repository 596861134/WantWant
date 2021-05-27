package com.want.want.fragment.me

import android.app.Application
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.databinding.MeFragmentBinding
import com.want.want.fragment.me.viewmodel.MeViewModel

class MeFragment : BaseVMRepositoryFragment<MeViewModel,MeFragmentBinding>(R.layout.me_fragment) {

    override fun getViewModel(app: Application): MeViewModel {
        return MeViewModel(app)
    }


}