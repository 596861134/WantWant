package com.want.want.activity.coin

import android.app.Application
import com.want.common.view.BaseVMRepositoryActivity
import com.want.want.R
import com.want.want.activity.coin.viewmodel.CoinRankViewModel
import com.want.want.databinding.ActivityCoinRankBinding

/**
 * 积分排行页面
 */
class CoinRankActivity : BaseVMRepositoryActivity<CoinRankViewModel,ActivityCoinRankBinding>(R.layout.activity_coin_rank) {

    override fun getViewModel(app: Application) = CoinRankViewModel(app)

}