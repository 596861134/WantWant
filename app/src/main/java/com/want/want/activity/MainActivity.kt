package com.want.want.activity

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.want.common.utils.isNotNull
import com.want.common.view.BaseViewModelActivity
import com.want.want.R
import com.want.want.adapter.MainVMAdapter
import com.want.want.databinding.ActivityMainBinding
import com.want.want.fragment.*
import com.want.want.viewmodel.CommonViewModel

class MainActivity : BaseViewModelActivity<CommonViewModel, ActivityMainBinding>(R.layout.activity_main, CommonViewModel::class.java) {

    private lateinit var viewPager:ViewPager2
    private var menu:MenuItem? = null
    private lateinit var bottomNavigation: BottomNavigationView
    private val mFragments = mutableListOf<Fragment>()

    override fun onViewInit() {
        super.onViewInit()
        viewPager = mBinding.viewPager
        bottomNavigation = mBinding.bottomNavigationView
        initFragment()
    }

    private fun initFragment(){
        if (mFragments.isNotEmpty()){
            mFragments.clear()
        }
        mFragments.add(HomeFragment())
        mFragments.add(QuestionsFragment())
        mFragments.add(CollectionFragment())
        mFragments.add(FindFragment())
        mFragments.add(MeFragment())

        viewPager.adapter = MainVMAdapter(mFragments, supportFragmentManager, lifecycle)
        viewPager.isUserInputEnabled = false

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    viewPager.setCurrentItem(0,false)
                }
                R.id.questionsFragment -> {
                    viewPager.setCurrentItem(1,false)
                }
                R.id.collectionFragment -> {
                    viewPager.setCurrentItem(2,false)
                }
                R.id.findFragment -> {
                    viewPager.setCurrentItem(3,false)
                }
                R.id.meFragment -> {
                    viewPager.setCurrentItem(4,false)
                }
            }
            false
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setButtonState(position)
            }
        })
    }

    private fun setButtonState(position: Int) {
        if (menu.isNotNull()){
            menu?.isChecked = false
        }else {
            bottomNavigation.menu.getItem(0).isChecked = false
        }
        menu = bottomNavigation.menu.getItem(position)
        menu?.isChecked = true
    }
}