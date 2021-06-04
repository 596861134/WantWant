package com.want.want.activity.tree

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.want.common.view.BaseVMRepositoryActivity
import com.want.want.R
import com.want.want.bean.CollectChangeBean
import com.want.want.databinding.ActivityTreeListBinding
import com.want.want.utils.GlobalSingle

class TreeListActivity : BaseVMRepositoryActivity<TreeListViewModel,ActivityTreeListBinding>(R.layout.activity_tree_list) {

    override fun getViewModel(app: Application): TreeListViewModel = TreeListViewModel(app)

    private val mObserver = Observer<CollectChangeBean> {
        mRealVM.updateCollectState(it)
    }

    override fun onResume() {
        super.onResume()
        GlobalSingle.onCollectChange.observe(this, mObserver)
    }

    override fun onPause() {
        super.onPause()
        GlobalSingle.onCollectChange.removeObserver(mObserver)
    }
}