package com.want.want.fragment.find.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.want.want.R
import com.want.want.fragment.find.viewmodel.FindContentWeChatViewModel

class FindContentWeChatFragment : Fragment() {

    companion object {
        fun newInstance() = FindContentWeChatFragment()
    }

    private lateinit var viewModel: FindContentWeChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.find_content_we_chat_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FindContentWeChatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}