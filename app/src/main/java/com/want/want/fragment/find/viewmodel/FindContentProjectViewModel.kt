package com.want.want.fragment.find.viewmodel

import android.app.Application
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.want.fragment.find.repository.FindContentProjectRepository

class FindContentProjectViewModel(app:Application) : BaseRepositoryViewModel<FindContentProjectRepository>(app,FindContentProjectRepository()) {
}