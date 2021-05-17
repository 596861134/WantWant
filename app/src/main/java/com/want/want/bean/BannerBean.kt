package com.want.want.bean

import android.app.Application
import androidx.databinding.ObservableField
import androidx.viewpager2.widget.ViewPager2
import com.want.common.CommonUtil
import com.want.common.utils.getResDimen
import com.want.common.utils.logWithTag
import com.want.common.viewmodel.BaseViewModel
import com.want.want.R
import com.want.want.adapter.BannerAdapter
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.indicator.Indicator
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.listener.OnPageChangeListener
import com.youth.banner.transformer.ZoomOutPageTransformer

/**
 * Created by chengzf on 2021/5/14.
 */
data class BannerBean(var imagePath: String?, var content: String?, var link: String?)

class BannerViewModel(app:Application):BaseViewModel(app){

    val mBannerHeight = ObservableField(R.dimen.dp_150.getResDimen().toInt())

    val mAdapterObservable = ObservableField<BannerAdapter>()

    val mIndicatorObservable = ObservableField<Indicator>(CircleIndicator(app))

    val mPageTransformer = ObservableField<ViewPager2.PageTransformer>(ZoomOutPageTransformer())

    val mBannerClickListener = OnBannerListener<BannerBean> {data, position ->
        "position = $position".logWithTag(CommonUtil.TAG)
    }

    var mCurrentPage = 0
    var mPageChangeListener = object : SimpleOnPageChangeListener() {
        override fun onPageSelected(position: Int) {
            mCurrentPage = position
        }
    }
}

abstract class SimpleOnPageChangeListener:OnPageChangeListener{

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {}

}