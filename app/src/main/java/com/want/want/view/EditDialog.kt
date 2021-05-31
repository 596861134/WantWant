package com.want.want.view

import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.KeyboardUtils
import com.want.common.view.dialog.BaseDialog
import com.want.want.R
import com.want.want.common.CollectContentPage
import com.want.want.common.EditDialogEventBean
import com.want.want.common.EditPage
import com.want.want.databinding.DialogEditBinding

/**
 * Created by chengzf on 2021/5/31.
 */
class EditDialog(val activity:AppCompatActivity) :BaseDialog<EditDialogViewModel,DialogEditBinding>(
    R.layout.dialog_edit, EditDialogViewModel::class.java, activity, R.style.EditDialogTheme) {

    fun showDialog(page: EditPage,bean: EditDialogEventBean?=null,collectContentPage: CollectContentPage){
        show()
        mRealVM.handlePageData(page,bean,collectContentPage)
        KeyboardUtils.showSoftInput(mBinding.edName)
    }

}