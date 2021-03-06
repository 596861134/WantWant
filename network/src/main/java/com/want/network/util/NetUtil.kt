package com.want.network.util

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.NetworkUtils
import com.want.common.utils.isNull
import com.want.common.utils.logWithTag
import com.want.common.utils.showToast
import com.want.common.viewmodel.BaseViewModel
import com.want.network.BaseBean
import com.want.network.NetConstant
import kotlinx.coroutines.launch

/**
 * @author jhb
 * @date 2020/10/27
 */
const val TAG = "NetUtil"

fun BaseViewModel.launch(showDialog: Boolean = true, finish: (suspend () -> Unit)? = null, error: (suspend () -> Unit)? = null, success: suspend () -> Unit) {
    viewModelScope.launch {
        try {
            dialogState(showDialog, true)
            success.invoke()
            dialogState(showDialog, false)
        } catch (e: Throwable) {
            if (error.isNull()) {
                dialogState(showDialog, false)
                e.netError()
            } else {
                error?.invoke()
            }
            e.printStackTrace()
            "网络错误: ${e.message}".logWithTag(TAG)
        } finally {
            finish?.invoke()
        }
    }
}

private fun BaseViewModel.dialogState(showDialog: Boolean, state: Boolean) {
    if (showDialog) {
        isDialogShow.value = state
    }

}

fun <T : BaseBean> response(bean: T, result: T.() -> Unit) {
    when (bean.errorCode) {
        NetConstant.SUCCESS -> result.invoke(bean)
        else -> bean.errorMsg?.showToast()
    }
}

fun loadSuccess() {
    WanExecutors.mDiskIO.execute {
        if (NetworkUtils.isAvailable()) {
            "加载成功...".showToast()
        }
    }
}

fun noMoreData() {
    "没有更多数据了...".showToast()
}

fun loginFirst() {
    "请先登录...".showToast()
}

fun Throwable.netError() {
    "网络错误: $message".showToast()
}

fun netError() {
    "网络错误...".showToast()
}

fun collectSuccess() {
    "收藏成功...".showToast()
}

fun cancelCollect() {
    "已取消收藏...".showToast()
}

fun logoutHint() {
    "您已经退出登录...".showToast()
}







