package com.want.common.view

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.want.common.utils.AppManager
import com.want.common.utils.isNull
import com.want.common.utils.truely
import com.want.common.view.dialog.LoadingDialog
import java.io.Serializable

/**
 * Created by chengzf on 2021/5/12.
 */
open class BaseActivity:AppCompatActivity() {

    private lateinit var mDialog:LoadingDialog

    private fun initDialog():LoadingDialog{
        if (mDialog.isNull()){
            mDialog = LoadingDialog(this)
        }
        return mDialog
    }

    fun showDialog(){
        initDialog()
        mDialog.show()
    }

    fun dismissDialog(){
        initDialog()
        if (mDialog.isShowing.truely()){
            mDialog.dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getAppManager().addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.getAppManager().finishActivity(this)
    }

    fun startActivity(clazz: Class<*>, vararg data: Pair<String, Any?>) {
        val intent = Intent(this, clazz)

        data.forEach {
            when (it.second) {
                is Boolean -> {
                    intent.putExtra(it.first, it.second as Boolean)
                }
                is Byte -> {
                    intent.putExtra(it.first, it.second as Byte)
                }
                is Int -> {
                    intent.putExtra(it.first, it.second as Int)
                }
                is Short -> {
                    intent.putExtra(it.first, it.second as Short)
                }
                is Long -> {
                    intent.putExtra(it.first, it.second as Long)
                }
                is Float -> {
                    intent.putExtra(it.first, it.second as Float)
                }
                is Double -> {
                    intent.putExtra(it.first, it.second as Double)
                }
                is Char -> {
                    intent.putExtra(it.first, it.second as Char)
                }
                is String -> {
                    intent.putExtra(it.first, it.second as String)
                }
                is Serializable -> {
                    intent.putExtra(it.first, it.second as Serializable)
                }
                is Parcelable -> {
                    intent.putExtra(it.first, it.second as Parcelable)
                }
            }
        }

        startActivity(intent)
    }

}