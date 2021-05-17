package com.want.want.utils

import androidx.lifecycle.MutableLiveData
import com.want.want.common.CollectChangeBean
import com.want.want.common.CollectContentPage
import com.want.want.common.EditDialogEvent

/**
 * @author jhb
 * @date 2020/11/2
 */
object GlobalSingle {

    val isLoginSuccess = MutableLiveData<Boolean>()
    val isLoginSuccessToCollect = MutableLiveData<CollectContentPage>()

    var onCollectChange = MutableLiveData<CollectChangeBean>()

    var onAddCollectWebsite = MutableLiveData<Boolean>()
    var onAddCollectArticle = MutableLiveData<CollectContentPage>()
    var onAddShareArticle = MutableLiveData<CollectContentPage>()

    var showEditDialog = MutableLiveData<EditDialogEvent>()

    var onFindPageSelect = MutableLiveData<Int>()
    var onCollectPageSelect = MutableLiveData<CollectContentPage>()


}
