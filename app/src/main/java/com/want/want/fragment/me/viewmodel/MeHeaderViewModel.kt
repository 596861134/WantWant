package com.want.want.fragment.me.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.want.common.ItemType
import com.want.common.rv.BaseMultiItemViewModel
import com.want.common.utils.randomInt
import com.want.want.AppApplication
import com.want.want.activity.login.LoginActivity

const val UN_LOGIN_PATH = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3386352505,2412195498&fm=26&gp=0.jpg"
//星爷
private const val PATH_1 = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=828005745,3289366977&fm=26&gp=0.jpg"
//刘亦菲
private const val PATH_3 = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2181944231,3260797126&fm=26&gp=0.jpg"


/**
 * Created by chengzf on 2021/5/27.
 */
class MeHeaderViewModel(app:Application) :BaseMultiItemViewModel(app) {

    override val itemType: Int = ItemType.ITEM_ME_HEADER

    private val imagePath = arrayOf(UN_LOGIN_PATH, PATH_1, PATH_3)
    var mUserName = ObservableField<String>()
    var mPath = ObservableField(UN_LOGIN_PATH)
    var mIdAndRank = ObservableField("")

    fun loadAvatar(){
        mPath.set(imagePath[randomInt(imagePath.size)])
    }

    fun login(){
        if (AppApplication.isLogin){
            return
        }
        startActivity(LoginActivity::class.java)
    }

}