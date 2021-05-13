package com.want.common.viewmodel

import android.app.Application
import com.want.common.interfaces.BaseRepository

/**
 * Created by chengzf on 2021/5/12.
 */
abstract class BaseRepositoryViewModel<T: BaseRepository>(app:Application, val mRepo:T):BaseLayoutViewModel(app) {

}