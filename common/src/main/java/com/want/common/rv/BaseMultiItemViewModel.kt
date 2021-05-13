package com.want.common.rv

import android.app.Application
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by chengzf on 2021/5/13.
 */
abstract class BaseMultiItemViewModel(app:Application) :QuickItemViewModel(app), MultiItemEntity {
}