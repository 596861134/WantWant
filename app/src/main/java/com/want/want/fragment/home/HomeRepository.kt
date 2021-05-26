package com.want.want.fragment.home

import com.blankj.utilcode.util.NetworkUtils
import com.want.network.util.netError
import com.want.network.util.response
import com.want.want.bean.ArrayDataBean
import com.want.want.bean.BannerDataBean
import com.want.want.bean.ObjectDataBean
import com.want.want.network.NetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

/**
 * Created by chengzf on 2021/5/14.
 */
class HomeRepository:NetRepository() {

    /**
     * 获取Banner轮播图
     */
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

    /**
     * 获取置顶文章
     */
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

    /**
     * 获取文章列表
     */
    suspend fun articleList(page:Int): ObjectDataBean.DataBean? {
        return withContext(Dispatchers.Default){
            async {
                var dataBean:ObjectDataBean.DataBean? = null
                if (NetworkUtils.isAvailable()){
                    response(api.articleList(page)){
                        dataBean = this.data
                        dataBean?.mLastTime = System.currentTimeMillis()
                    }
                }else{
                    netError()
                }
                dataBean
            }
        }.await()
    }
}