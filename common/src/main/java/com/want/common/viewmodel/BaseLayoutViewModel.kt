package com.want.common.viewmodel

import android.app.Application
import com.want.common.BR
import com.want.common.interfaces.VariableId

/**
 * Created by chengzf on 2021/5/12.
 */
open class BaseLayoutViewModel(app:Application):BaseViewModel(app),VariableId {

    override fun id(): Int = BR.layout
}