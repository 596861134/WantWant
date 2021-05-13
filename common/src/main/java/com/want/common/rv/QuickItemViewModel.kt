package com.want.common.rv

import android.app.Application
import com.want.common.BR
import com.want.common.interfaces.ItemClick
import com.want.common.interfaces.VariableId
import com.want.common.viewmodel.BaseViewModel

/**
 * Created by chengzf on 2021/5/13.
 */
open class QuickItemViewModel(app:Application):BaseViewModel(app),ItemClick,VariableId {

    override fun onItemClick() {
    }

    override fun id(): Int = BR.item
}