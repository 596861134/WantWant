package com.want.want.fragment.find.viewmodel

import android.app.Application
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.want.fragment.find.repository.FindContentNaviRepository

class FindContentNaviViewModel(app:Application) : BaseRepositoryViewModel<FindContentNaviRepository>(app,FindContentNaviRepository()) {

}