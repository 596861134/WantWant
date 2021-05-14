package com.want.want.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.want.want.bean.BannerBean
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder

/**
 * Created by chengzf on 2021/5/14.
 */
class BannerAdapter(mData:MutableList<BannerBean>) :BannerImageAdapter<BannerBean>(mData) {

    override fun onBindView(holder: BannerImageHolder, data: BannerBean?, position: Int, size: Int) {
        Glide.with(holder.itemView)
            .load(data?.imagePath)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
            .into(holder.imageView)
    }

}