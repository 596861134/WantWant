package com.want.want.fragment.find.repository

import com.want.want.network.NetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by chengzf on 2021/6/3.
 */
class FindContentProjectRepository:NetRepository() {

    suspend fun projectList(page:Int) = withContext(Dispatchers.IO){
        api.projectList(page)
    }
}