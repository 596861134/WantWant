package com.want.want.fragment.find.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.want.want.R
import com.want.want.fragment.find.viewmodel.FindContentProjectTreeViewModel

class FindContentProjectTreeFragment : Fragment() {

    companion object {
        fun newInstance() = FindContentProjectTreeFragment()
    }

    private lateinit var viewModel: FindContentProjectTreeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.find_content_project_tree_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FindContentProjectTreeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}