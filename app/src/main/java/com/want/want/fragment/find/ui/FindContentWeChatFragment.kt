package com.want.want.fragment.find.ui

import android.app.Application
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.databinding.FindContentWeChatFragmentBinding
import com.want.want.fragment.find.viewmodel.FindContentWeChatViewModel

class FindContentWeChatFragment : BaseVMRepositoryFragment<FindContentWeChatViewModel,FindContentWeChatFragmentBinding>(R.layout.find_content_we_chat_fragment) {

    override fun getViewModel(app: Application): FindContentWeChatViewModel = FindContentWeChatViewModel(app)


}