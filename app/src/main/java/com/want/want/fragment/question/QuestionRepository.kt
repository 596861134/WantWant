package com.want.want.fragment.question

import com.want.want.network.NetRepository

/**
 * Created by chengzf on 2021/5/26.
 */
class QuestionRepository: NetRepository() {

    suspend fun questionList(page:Int) = api.wendaList(page)
}