package com.want.want.utils

import android.app.Activity
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.want.common.utils.isNull

/**
 * Created by chengzf on 2021/6/1.
 */
object DialogUtil {
    fun showDialog(
        activity: Activity,
        title: String = "提示",
        message: String,
        confirmText: String = "确定",
        cancelText: String = "取消",
        positionAction: (DialogInterface) -> Unit,
        negativeAction: ((DialogInterface) -> Unit)? = null
    ) {
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(confirmText){dialog,which ->
                positionAction.invoke(dialog)
            }
            .setNegativeButton(cancelText){dialog,which ->
                if (negativeAction.isNull()){
                    dialog.dismiss()
                }else{
                    negativeAction?.invoke(dialog)
                }
            }
            .show()
    }
}