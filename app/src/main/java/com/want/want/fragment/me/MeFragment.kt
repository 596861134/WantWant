package com.want.want.fragment.me

import android.app.Application
import androidx.lifecycle.Observer
import com.blankj.utilcode.util.KeyboardUtils
import com.want.common.view.BaseVMRepositoryFragment
import com.want.want.R
import com.want.want.common.EditDialogEvent
import com.want.want.common.EditPage
import com.want.want.databinding.MeFragmentBinding
import com.want.want.fragment.me.viewmodel.MeViewModel
import com.want.want.utils.GlobalSingle
import com.want.want.view.EditDialog

class MeFragment : BaseVMRepositoryFragment<MeViewModel,MeFragmentBinding>(R.layout.me_fragment) {

    private val mDialog by lazy { EditDialog(mActivity) }

    override fun getViewModel(app: Application) = MeViewModel(app)

    private var mObserver = Observer<Boolean>{
        if (it){
            mRealVM.coinUserInfo()
        }else{
            mRealVM.resetLoginState()
        }
    }

    private var mDialogObserver = Observer<EditDialogEvent>{
        if (it.page != EditPage.NONE){
            mDialog.showDialog(it.page,it.bean,it.collectContentPage)
        }else {
            mDialog.dismiss()
            KeyboardUtils.hideSoftInputByToggle(mActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        GlobalSingle.isLoginSuccess.observe(this, mObserver)
        GlobalSingle.showEditDialog.observe(this, mDialogObserver)
    }

    override fun onPause() {
        super.onPause()
        GlobalSingle.isLoginSuccess.removeObserver(mObserver)
        GlobalSingle.showEditDialog.removeObserver(mDialogObserver)

    }
}