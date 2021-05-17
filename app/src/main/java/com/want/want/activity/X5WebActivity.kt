package com.want.want.activity

import android.app.Application
import android.graphics.Bitmap
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.want.common.CommonUtil
import com.want.common.utils.logWithTag
import com.want.common.utils.truely
import com.want.common.view.BaseVMRepositoryActivity
import com.want.want.R
import com.want.want.databinding.ActivityWebviewX5Binding
import com.want.want.viewmodel.X5WebViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class X5WebActivity : BaseVMRepositoryActivity<X5WebViewModel, ActivityWebviewX5Binding>(R.layout.activity_webview_x5) {

    companion object {
        private const val FILTER_URL_JIAN_SHU = "jianshu://notes/"
    }

    private lateinit var webView:WebView

    override fun getViewModel(app: Application): X5WebViewModel = X5WebViewModel(app)

    override fun onViewInit() {
        super.onViewInit()
        setWebView()
    }

    override fun onEvent() {
        super.onEvent()
        mRealVM.mScrollToTop.observe(this, Observer {
            if (it){
                mBinding.nsv.smoothScrollTo(0,0,800)
                mRealVM.mScrollToTop.value = false
            }
        })
    }

    private fun setWebView() {
        webView = mBinding.webView
        WebView.setWebContentsDebuggingEnabled(true)
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.setAppCacheMaxSize((1024 * 1024 * 4).toLong())
        val absolutePath = applicationContext.cacheDir.absolutePath
        settings.setAppCachePath(absolutePath)
        settings.allowFileAccess = true
        settings.setAppCacheEnabled(true)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(webView: WebView?, url: String?): Boolean {
                "shouldOverrideUrlLoading url = $url".logWithTag(CommonUtil.TAG)
                return when {
                    url?.contains(FILTER_URL_JIAN_SHU).truely() -> {
                        true
                    }
                    else -> false
                }
            }

            override fun onPageFinished(webView: WebView?, url: String?) {
                super.onPageFinished(webView, url)
                "onPageFinished url = $url".logWithTag(CommonUtil.TAG)
                dismissDialog()
            }

            override fun onPageStarted(webView: WebView?, url: String?, bitmap: Bitmap?) {
                super.onPageStarted(webView, url, bitmap)
                "onPageStarted url = $url".logWithTag(CommonUtil.TAG)
                showDialog()
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(webView: WebView?, progress: Int) {
                super.onProgressChanged(webView, progress)
                "onProgressChanged progress = $progress".logWithTag(CommonUtil.TAG)
                if (progress >= 100) {
                    dismissDialog()
                }
            }
        }
    }

    override fun onBackPressed() {
        Observable.timer(200,TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (webView.canGoBack()){
                    webView.goBack()
                }else {
                    if (!isFinishing){
                        super.onBackPressed()
                    }
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        (webView.parent as ViewGroup).removeView(webView)
        webView.stopLoading()
        webView.loadUrl(null)
        webView.clearHistory()
        webView.removeAllViews()
        webView.pauseTimers()
        webView.clearCache(true)
        webView.settings.javaScriptEnabled = false
        webView.tag = null
        webView.destroy()
    }

}