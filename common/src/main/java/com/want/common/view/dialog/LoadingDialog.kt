package com.want.common.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import com.want.common.BaseApplication
import com.want.common.R

/**
 * Created by chengzf on 2021/5/12.
 */
class LoadingDialog(context: Context,private val isFullScreen:Boolean = false)
    :AppCompatDialog(context, R.style.LoadingDialogTheme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isFullScreen){
            window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        window?.statusBarColor = ContextCompat.getColor(BaseApplication.getContext(), R.color.colorAccent)
        setContentView(R.layout.dialog_loading)
        setCancelable(false)
        setCanceledOnTouchOutside(true)
    }
}