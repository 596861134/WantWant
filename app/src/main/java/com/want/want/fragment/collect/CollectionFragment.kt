package com.want.want.fragment.collect

import com.want.common.view.BaseViewModelFragment
import com.want.want.R
import com.want.want.databinding.CollectionFragmentBinding
import com.want.want.rv.RvScrollToTop

class CollectionFragment : BaseViewModelFragment<CollectionViewModel,CollectionFragmentBinding>(
    R.layout.collection_fragment,CollectionViewModel::class.java) ,RvScrollToTop{



    override fun bindScrollListener() {
        TODO("Not yet implemented")
    }

    override fun scrollToTop() {
        TODO("Not yet implemented")
    }


}