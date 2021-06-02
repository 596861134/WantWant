package com.want.want.fragment.collect

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.want.common.view.BaseViewModelFragment
import com.want.want.R
import com.want.want.adapter.FragmentAdapter
import com.want.want.common.CollectContentPage
import com.want.want.databinding.CollectionFragmentBinding
import com.want.want.fragment.collect.viewmodel.CollectionViewModel
import com.want.want.rv.RvScrollToTop
import com.want.want.utils.GlobalSingle
import com.youth.banner.transformer.ZoomOutPageTransformer

class CollectionFragment : BaseViewModelFragment<CollectionViewModel,CollectionFragmentBinding>(
    R.layout.collection_fragment, CollectionViewModel::class.java) ,RvScrollToTop{

    private val mFragments = arrayListOf<CollectContentFragment>()
    private val mTitles = arrayListOf("收藏文章","面试相关","分享文章","收藏网站")

    override fun onViewInit() {
        super.onViewInit()
        if (mFragments.isNotEmpty()){
            mFragments.clear()
        }

        mFragments.add(initFragment(CollectContentPage.COLLECT_ARTICLE))
        mFragments.add(initFragment(CollectContentPage.INTERVIEW_RELATE))
        mFragments.add(initFragment(CollectContentPage.SHARE_ARTICLE))
        mFragments.add(initFragment(CollectContentPage.COLLECT_WEBSITE))

        mBinding.viewPager2.adapter = FragmentAdapter(mFragments,childFragmentManager,lifecycle)
        mBinding.viewPager2.setPageTransformer(ZoomOutPageTransformer())
        //把TabLayout和Viewpager绑定
        TabLayoutMediator(mBinding.tabLayout,mBinding.viewPager2) { tab, position ->
            tab.text = mTitles[position]
        }.attach()
    }

    override fun onEvent() {
        super.onEvent()
        mBinding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mBinding.viewPager2.setCurrentItem(tab?.position ?: 0, false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        mBinding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                GlobalSingle.onCollectPageSelect.value = getPageByIndex(position)
            }
        })
    }

    fun getPageByIndex(index: Int) = when (index) {
        0 -> CollectContentPage.COLLECT_ARTICLE
        1 -> CollectContentPage.INTERVIEW_RELATE
        2 -> CollectContentPage.SHARE_ARTICLE
        3 -> CollectContentPage.COLLECT_WEBSITE
        4 -> CollectContentPage.SHARE_PROJECT
        else -> CollectContentPage.NONE
    }

    private fun initFragment(page:CollectContentPage):CollectContentFragment{
        val collectContentFragment = CollectContentFragment()
        val bundle = Bundle()
        bundle.putSerializable(CollectContentFragment.CONTENT_PAGE,page)
        collectContentFragment.arguments = bundle
        return collectContentFragment
    }

    override fun bindScrollListener() {
        //nothing
    }

    override fun scrollToTop() {
        (mFragments[mBinding.viewPager2.currentItem] as? RvScrollToTop)?.scrollToTop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            val mRv = mBinding.viewPager2::class.java.getDeclaredField("mRecyclerView")
            mRv.isAccessible = true
            (mRv as? RecyclerView)?.recycledViewPool?.clear()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


}