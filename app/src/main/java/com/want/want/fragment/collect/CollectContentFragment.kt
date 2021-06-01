package com.want.want.fragment.collect

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.want.want.R

class CollectContentFragment : Fragment() {

    companion object {
        const val CONTENT_PAGE = "content_page"
    }

    private lateinit var viewModel: CollectContentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.collect_content_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CollectContentViewModel::class.java)
        // TODO: Use the ViewModel
    }

}