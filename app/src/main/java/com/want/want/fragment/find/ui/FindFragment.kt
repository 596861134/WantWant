package com.want.want.fragment.find.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.want.common.view.BaseViewModelFragment
import com.want.want.R
import com.want.want.adapter.FragmentAdapter
import com.want.want.databinding.FindFragmentBinding
import com.want.want.fragment.find.viewmodel.FindViewModel
import com.want.want.rv.RvScrollToTop
import com.want.want.utils.GlobalSingle
import com.youth.banner.transformer.ZoomOutPageTransformer

class FindFragment
    : BaseViewModelFragment<FindViewModel,FindFragmentBinding>(R.layout.find_fragment,FindViewModel::class.java),
    RvScrollToTop {

    private val mFragments = arrayListOf<Fragment>()
    private val mTitles = arrayOf("体系", "导航", "公众号", "项目", "项目分类")

    override fun onViewInit() {
        super.onViewInit()
        if (mFragments.isNotEmpty()){
            mFragments.clear()
        }
        mFragments.add(FindContentTreeFragment())
        mFragments.add(FindContentNaviFragment())
        mFragments.add(FindContentWeChatFragment())
        mFragments.add(FindContentProjectFragment())
        mFragments.add(FindContentProjectTreeFragment())

        mBinding.viewPager2.adapter = FragmentAdapter(mFragments,childFragmentManager,lifecycle)
        mBinding.viewPager2.setPageTransformer(ZoomOutPageTransformer())
        TabLayoutMediator(mBinding.tabLayout,mBinding.viewPager2){tab, position ->
            tab.text = mTitles[position]
        }.attach()
    }

    override fun onEvent() {
        super.onEvent()
        mBinding.tabLayout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                mBinding.viewPager2.setCurrentItem(tab?.position ?: 0,false)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        mBinding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                GlobalSingle.onFindPageSelect.value = position
            }
        })
    }

    override fun bindScrollListener() {
    }

    override fun scrollToTop() {
        (mFragments[mBinding.viewPager2.currentItem] as? RvScrollToTop)?.scrollToTop()
    }


}