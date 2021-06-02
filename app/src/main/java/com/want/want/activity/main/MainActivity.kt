package com.want.want.activity.main

import android.view.KeyEvent
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.want.common.utils.isNotNull
import com.want.common.utils.showToast
import com.want.common.view.BaseViewModelActivity
import com.want.want.R
import com.want.want.adapter.FragmentAdapter
import com.want.want.databinding.ActivityMainBinding
import com.want.want.fragment.*
import com.want.want.fragment.collect.CollectionFragment
import com.want.want.fragment.home.HomeFragment
import com.want.want.fragment.me.MeFragment
import com.want.want.fragment.question.QuestionsFragment
import com.want.want.rv.RvScrollToTop

class MainActivity : BaseViewModelActivity<MainViewModel, ActivityMainBinding>(R.layout.activity_main, MainViewModel::class.java) {

    private lateinit var viewPager:ViewPager2
    private var menu:MenuItem? = null
    private lateinit var bottomNavigation: BottomNavigationView
    private val mFragments = mutableListOf<Fragment>()
    private var mPagePosition:Int = 0
    private var lastExitRequestTime:Long = 0L

    override fun onViewInit() {
        super.onViewInit()
        viewPager = mBinding.viewPager
        bottomNavigation = mBinding.bottomNavigationView
        initFragment()
    }

    override fun onEvent() {
        super.onEvent()
        mRealVM.mFabClick.observe(this, Observer {
            if (it) {
                (mFragments[mPagePosition] as? RvScrollToTop)?.scrollToTop()
                mRealVM.mFabClick.value = false
            }
        })
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

        viewPager.adapter = FragmentAdapter(mFragments, supportFragmentManager, lifecycle)
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
        mRealVM.mFabVisible.set(false)
        mPagePosition = position
        if (menu.isNotNull()){
            menu?.isChecked = false
        }else {
            bottomNavigation.menu.getItem(0).isChecked = false
        }
        menu = bottomNavigation.menu.getItem(position)
        menu?.isChecked = true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            exitOnSecondTime()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun exitOnSecondTime(){
        lastExitRequestTime = if (System.currentTimeMillis() - lastExitRequestTime <= 2000){
            goHome()
            0L
        }else {
            "再次点击返回退出".showToast()
            System.currentTimeMillis()
        }
    }

    private fun goHome() {
        moveTaskToBack(true)
    }
}