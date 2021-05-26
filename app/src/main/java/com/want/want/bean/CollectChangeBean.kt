package com.want.want.bean

import com.want.common.utils.showToast
import com.want.common.viewmodel.BaseLayoutViewModel
import com.want.network.NetConstant
import com.want.network.util.collectSuccess
import com.want.network.util.launch
import com.want.want.activity.login.LoginActivity
import com.want.want.network.NetRepository
import com.want.want.utils.GlobalSingle

/**
 * Created by chengzf on 2021/5/17.
 */
data class CollectChangeBean(var isCollect: Boolean, var id: Int)

fun BaseLayoutViewModel.collectDelegate(id: Int,repo:NetRepository,success:(()->Unit)? = null){
    launch {
        val bean = repo.collect(id)
        when(bean.errorCode){
            NetConstant.SUCCESS -> {
                collectSuccess()
                GlobalSingle.onCollectChange.value = CollectChangeBean(true, id)
                success?.invoke()
            }
            NetConstant.UN_LOGIN -> {
                bean.errorMsg?.showToast()
                startActivity(LoginActivity::class.java)
            }
            else -> {
                bean.errorMsg?.showToast()
            }
        }
    }
}

fun BaseLayoutViewModel.unCollectDelegate(id: Int, repo: NetRepository, isOnMe: Boolean = false, originId: Int = -1, success: (() -> Unit)? = null){
    launch {
        val bean = repo.unCollect(id, isOnMe, originId)
        when(bean.errorCode){
            NetConstant.SUCCESS -> {
                collectSuccess()
                GlobalSingle.onCollectChange.value = CollectChangeBean(true, if (isOnMe) originId else id)
                success?.invoke()
            }
            NetConstant.UN_LOGIN -> {
                bean.errorMsg?.showToast()
                startActivity(LoginActivity::class.java)
            }
            else -> {
                bean.errorMsg?.showToast()
            }
        }
    }
}