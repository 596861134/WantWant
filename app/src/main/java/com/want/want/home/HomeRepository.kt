package com.want.want.home

import com.want.network.util.response
import com.want.want.bean.ArrayDataBean
import com.want.want.bean.BannerDataBean
import com.want.want.network.NetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * Created by chengzf on 2021/5/14.
 */
class HomeRepository:NetRepository() {

    suspend fun banner() :BannerDataBean?{
        return withContext(Dispatchers.Default){
            async {
                var bannerDataBean:BannerDataBean? = null
                response(api.banner()){
                    bannerDataBean = this
                    bannerDataBean?.mLastTime = System.currentTimeMillis()
                }
                bannerDataBean
            }
        }.await()
    }

    suspend fun articleTop(): ArrayDataBean?{
        return withContext(Dispatchers.Default){
            async {
                var arrayDataBean:ArrayDataBean? = null
                response(api.articleTop()){
                    arrayDataBean = this
                    arrayDataBean?.mLastTime = System.currentTimeMillis()
                }
                arrayDataBean
            }
        }.await()
    }
}