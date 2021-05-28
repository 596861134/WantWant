package com.want.want.activity.coin

import com.want.want.network.NetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by chengzf on 2021/5/28.
 */
class CoinRankRepository:NetRepository() {

    suspend fun coinRankList(page:Int) = withContext(Dispatchers.IO){
        api.coinRankList(page)
    }
}