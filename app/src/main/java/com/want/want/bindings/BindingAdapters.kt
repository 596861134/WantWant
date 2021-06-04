package com.want.want.bindings

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tencent.smtt.sdk.WebView
import com.want.common.CommonUtil
import com.want.common.utils.isNull
import com.want.common.utils.logWithTag
import com.want.want.R
import com.want.want.adapter.BannerAdapter
import com.youth.banner.Banner
import com.youth.banner.indicator.Indicator
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.listener.OnPageChangeListener

@BindingAdapter(value = ["onRefreshListener"], requireAll = false)
fun onRefreshListener(swipeRefreshLayout: SwipeRefreshLayout, listener: (() -> Unit)?) {
    swipeRefreshLayout.setOnRefreshListener {
        listener?.invoke()
    }
}

@BindingAdapter("rvScrollToTop")
fun rvScrollToTop(recyclerView: RecyclerView, isToTop: Boolean) {
    if (isToTop) {
        recyclerView.smoothScrollToPosition(0)
    }
}

@BindingAdapter(value = ["setLoadMoreListener"], requireAll = false)
fun setLoadMoreListener(recyclerView: RecyclerView, listener: (() -> Unit)?) {

    var isToTop = false

    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            val lm = recyclerView.layoutManager
            if (lm is LinearLayoutManager) {
                val lastVisibleItemPosition = lm.findLastVisibleItemPosition()
                val totalItemCount = recyclerView.adapter?.itemCount ?: 0
                val visibleChildCount = recyclerView.childCount

                if (
                        isToTop &&
                        newState == RecyclerView.SCROLL_STATE_IDLE &&
                        totalItemCount != 0 &&
                        lastVisibleItemPosition == totalItemCount - 1 &&
                        visibleChildCount != 0
                ) {
                    listener?.invoke()
                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            isToTop = dy > 0
        }
    })
}

@BindingAdapter("layoutHeight")
fun layoutHeight(view: View, dp:Int){
    val layoutParams = view.layoutParams
    layoutParams.height = dp
    view.layoutParams = layoutParams
}

@BindingAdapter("layoutWidth")
fun layoutWidth(view: View, dp: Int) {
    val layoutParams = view.layoutParams
    layoutParams.width = dp
    view.layoutParams = layoutParams
}

@BindingAdapter("setBannerAdapter")
fun setBannerAdapter(banner:Banner<*,BannerAdapter>,adapter: BannerAdapter){
    if (banner.adapter != adapter){
        banner.adapter = adapter
    }
}

@BindingAdapter("setBannerClickListener")
fun setBannerClickListener(banner: Banner<*, BannerAdapter>, listener: OnBannerListener<*>?){
    if (listener.isNull()) return
    banner.setOnBannerListener(listener)
}

@BindingAdapter("setBannerIndicator")
fun setBannerIndicator(banner: Banner<*, BannerAdapter>, indicator: Indicator){
    banner.indicator = indicator
}

@BindingAdapter("setBannerPageChangeListener")
fun setBannerPageChangeListener(banner: Banner<*, BannerAdapter>, listener: OnPageChangeListener){
    if (listener.isNull()) return
    banner.addOnPageChangeListener(listener)
}

@BindingAdapter("setPageTransformer")
fun setPageTransformer(banner: Banner<*, BannerAdapter>, transformer: ViewPager2.PageTransformer){
    banner.setPageTransformer(transformer)
}

@BindingAdapter("fabCustomSize")
fun fabCustomSize(fab: FloatingActionButton, dp: Int) {
    fab.customSize = dp
}

@BindingAdapter("webViewLoadUrl")
fun webViewLoadUrl(webView: WebView, url: String?) {
    "webViewLoadUrl url = $url".logWithTag(CommonUtil.TAG)
    if (url.isNullOrEmpty()) return
    webView.loadUrl(url)
}

@BindingAdapter("setAvatar")
fun setAvatar(image:ImageView,path:String?){
    path?.let {
        "path:${path}".logWithTag(CommonUtil.TAG)
        Glide.with(image)
            .applyDefaultRequestOptions(RequestOptions().circleCrop())
            .load(it)
            .into(image)
    }
}

@BindingAdapter("loadUrl", requireAll = false)
fun loadUrl(imageView: ImageView, path: String?) {
    Glide.with(imageView)
        .applyDefaultRequestOptions(RequestOptions()
            .placeholder(R.mipmap.place_holder)
            .centerCrop())
        .load(path).into(imageView)
}

@BindingAdapter("etSetSelect")
fun etSetSelect(editText: EditText,position:Int){
    editText.setSelection(position)
}

