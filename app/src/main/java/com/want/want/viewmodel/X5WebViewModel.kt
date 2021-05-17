package com.want.want.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.want.common.utils.getResDrawable
import com.want.common.utils.truely
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.want.R
import com.want.want.bean.collectDelegate
import com.want.want.bean.unCollectDelegate
import com.want.want.common.CommonItemBean
import com.want.want.network.NetRepository

/**
 * Created by chengzf on 2021/5/17.
 */
class X5WebViewModel(app:Application):BaseRepositoryViewModel<NetRepository>(app, NetRepository()) {

    companion object {
        const val FLAG_BEAN = "flag_bean"
        const val FLAG_SHOW_COLLECT_ICON = "flag_show_collect_icon"
    }

    val mScrollToTop = MutableLiveData<Boolean>()

    val mUrl = ObservableField("")

    private var mBean:CommonItemBean? = null

    private var showCollectIcon = true

    val mFabVM = FabViewModel(
        onClick = {
            mScrollToTop.value = true
        }
    )

    val mTitleVM = TitleViewModel(
        leftAction = {
            finish()
        },
        title = "",
        rightAction = {
            if (mBean?.collect.truely()){
                mBean?.id?.let {
                    unCollectDelegate(it,mRepo,success = {
                        mBean?.collect = false
                        setCollectState(false)
                    })
                }
            }else{
                mBean?.id?.let {
                    collectDelegate(it,mRepo,success = {
                        mBean?.collect = true
                        setCollectState(true)
                    })
                }
            }
        }
    )

    override fun onModelBind() {
        super.onModelBind()
        mBean = mBundle.getSerializable(FLAG_BEAN) as? CommonItemBean
        showCollectIcon = mBundle.getBoolean(FLAG_SHOW_COLLECT_ICON,true)
        mBean?.apply {
            mUrl.set(link)
            mTitleVM.mTitle.set(title)
            setCollectState(collect)
        }
    }

    private fun setCollectState(collect: Boolean) {
        if (showCollectIcon) {
            mTitleVM.mRightDrawable.set(if (collect) R.drawable.collect_red.getResDrawable() else R.mipmap.sc_white_stroke_ico.getResDrawable())
        } else {
            mTitleVM.mRightDrawable.set(null)
        }
    }

}