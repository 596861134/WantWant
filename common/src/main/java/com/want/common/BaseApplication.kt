package com.want.common

import android.app.Application
import android.content.Context

/**
 * Created by chengzf on 2021/5/12.
 */
open class BaseApplication:Application() {

    companion object{
        private lateinit var mContext:Context

        fun getContext():Context{
            return mContext
        }

        fun isDebug():Boolean{
            return true
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this
        CommonUtil.initCommon()
    }

}