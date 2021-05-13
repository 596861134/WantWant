package com.want.common

import com.want.common.log.CLog
import com.want.common.utils.CrashHandler
import com.want.common.utils.MMKVUtil

/**
 * Created by chengzf on 2021/5/12.
 */
object CommonUtil {

    fun initCommon(){
        CLog.init(BaseApplication.isDebug())
        CrashHandler.getInstance().init(BaseApplication.getContext())
        MMKVUtil.init(BaseApplication.getContext())
    }

}