package com.want.want.activity.coin.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import com.want.common.rv.QuickAdapter
import com.want.common.viewmodel.BaseRepositoryViewModel
import com.want.network.util.launch
import com.want.network.util.loadSuccess
import com.want.network.util.noMoreData
import com.want.network.util.response
import com.want.want.R
import com.want.want.activity.coin.CoinRankRepository
import com.want.want.bean.CoinUserInfoBean
import com.want.want.rv.RecyclerViewVM
import com.want.want.viewmodel.TitleViewModel

/**
 * Created by chengzf on 2021/5/28.
 */
class CoinRankViewModel(app:Application):BaseRepositoryViewModel<CoinRankRepository>(app, CoinRankRepository()) {
    companion object {
        const val COIN_USER_INFO_BEAN = "coin_user_info_bean"
    }

    var mTitleVM = TitleViewModel(
        leftAction = {
            finish()
        },
        title = "积分排行榜"
    )

    var mRank = ObservableField("")
    var mName = ObservableField("")
    var mCount = ObservableField("")

    var mData = arrayListOf<ItemCoinRankVM>()
    val mAdapter = QuickAdapter(R.layout.item_rv_coin_rank,mData)

    var mCurrentPage = 1
    var mCountPage = 1

    var rvVM = RecyclerViewVM(app).apply {
        mRefreshEnable = true
        mAdapterObservable.set(mAdapter)

        mOnRefresh = {
            mIsRefreshing.set(true)
            mCurrentPage = 1
            requestServer(false)
        }

        mOnLoadMoreListener = {
            mCurrentPage++
            if (mCurrentPage<mCountPage){
                requestServer(true)
            }else{
                noMoreData()
            }
        }
    }

    override fun onModelBind() {
        super.onModelBind()
        (mBundle.getSerializable(COIN_USER_INFO_BEAN) as? CoinUserInfoBean.Data)?.apply {
            mRank.set(rank)
            mName.set(username)
            mCount.set(coinCount?.toString())
        }
        requestServer(true)
    }

    private fun requestServer(showDialog: Boolean) {
        launch(showDialog){
            response(mRepo.coinRankList(mCurrentPage)){
                if (!showDialog){
                    mData.clear()
                    rvVM.mIsRefreshing.set(false)
                }

                mCountPage = data?.pageCount ?: 1
                data?.datas?.forEach {
                    mData.add(ItemCoinRankVM(getApplication()).apply{
                        mRank.set(it.rank)
                        mName.set(it.username)
                        mCount.set(it.coinCount?.toString())
                    })
                }
                mAdapter.notifyDataSetChanged()
                loadSuccess()
            }
        }

    }


}