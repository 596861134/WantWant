package com.want.want.fragment.question

import android.app.Application
import com.want.common.rv.QuickAdapter
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.launch
import com.want.network.util.loadSuccess
import com.want.network.util.response
import com.want.want.R
import com.want.want.bean.CollectChangeBean
import com.want.want.bean.ItemDatasBean
import com.want.want.bean.collectDelegate
import com.want.want.bean.unCollectDelegate
import com.want.want.fragment.home.viewmodel.ItemHomeVM
import com.want.want.rv.RecyclerViewVM
import com.want.want.viewmodel.TitleViewModel

class QuestionsViewModel(app:Application) : BaseRepositoryViewModel<QuestionRepository>(app,QuestionRepository()) {

    var mData = arrayListOf<ItemHomeVM>()
    val mAdapter = QuickAdapter(R.layout.item_rv_home,mData)
    private var mCurrPage = 0

    val mTitleVM = TitleViewModel(
        leftDrawable = null,
        title = "问答"
    )

    val rvVM = RecyclerViewVM(app).apply {
        mRefreshEnable = true
        mAdapterObservable.set(mAdapter)
        mOnRefresh = {
            mIsRefreshing.set(true)
            mCurrPage = 0
            requestServer(false)
        }

        mOnLoadMoreListener = {
            mCurrPage++
            requestServer(true)
        }
    }

    override fun onModelBind() {
        super.onModelBind()
        requestServer(true)
    }

    private fun requestServer(showDialog: Boolean = true) {
        launch(showDialog){
            response(mRepo.questionList(mCurrPage)){
                resetDataIfNeed(showDialog)
                data?.datas?.forEach {
                    bindData(it)
                }
                mAdapter.notifyDataSetChanged()

                if (!showDialog){
                    rvVM.mIsRefreshing.set(false)
                }else{
                    loadSuccess()
                }
            }
        }
    }

    private fun resetDataIfNeed(showDialog: Boolean) {
        if (!showDialog){
            mData.clear()
            mAdapter.notifyDataSetChanged()
        }
    }


    fun updateCollectState(bean: CollectChangeBean) {
        mData.find { it.mId == bean.id }?.mCollect?.set(bean.isCollect)
    }

    private fun bindData(bran: ItemDatasBean) {
        mData.add(ItemHomeVM(getApplication(),bran).apply {
            bindData()
            onCollectClick = {
                if (mCollect.get()){
                    mId?.let {
                        unCollectDelegate(it,mRepo)
                    }
                }else {
                    mId?.let {
                        //收藏
                        collectDelegate(it,mRepo)
                    }
                }
            }
        })
    }

}