package com.want.want.rv

import android.app.Application
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.want.common.rv.QuickViewHolder

/**
 * Created by chengzf on 2021/5/14.
 */
open class RecyclerViewVM(app:Application) {
    var mRefreshEnable = false
    var mIsRefreshing = ObservableField(false)

    var mOnRefresh = {}

    var mOnLoadMoreListener = {}

    var mAdapterObservable: ObservableField<RecyclerView.Adapter<QuickViewHolder>> = ObservableField()
    var mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(app)

    var mScrollToTop = ObservableField(false)
    var mOnScrollListener = ObservableField(RvScrollListener())

}

open class RvScrollListener : RecyclerView.OnScrollListener()