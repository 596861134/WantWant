package com.want.want.activity.login

import android.app.Application
import androidx.lifecycle.Observer
import com.want.common.view.BaseVMRepositoryActivity
import com.want.want.R
import com.want.want.activity.login.viewmodel.LoginViewModel
import com.want.want.databinding.ActivityLoginBinding
import com.want.want.utils.GlobalSingle

class LoginActivity : BaseVMRepositoryActivity<LoginViewModel, ActivityLoginBinding>(R.layout.activity_login) {

    override fun getViewModel(app: Application): LoginViewModel {
        return LoginViewModel(app)
    }

    override fun onEvent() {
        super.onEvent()
        GlobalSingle.isLoginSuccess.observe(this, Observer<Boolean> {
            if (it){ finish() }
        })
    }


}