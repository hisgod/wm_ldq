package com.aib.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wm.loan.R

/**
 * Glide工具类
 */
object GlideUtils {
    /**
     * 加载圆形图片
     */
    fun loadRoundImage(iv: ImageView, url: String) {
        Glide
                .with(iv.context)
                .load(url)
                .apply(RequestOptions()
                        .circleCrop()
                        .error(R.drawable.ic_img_error)
                        .placeholder(R.drawable.ic_img_loading))
                .into(iv)
    }

    /**
     * 加载普通图片
     */
    fun loadCommonImage(iv: ImageView, url: String) {
        Glide
                .with(iv.context)
                .load(url)
                .apply(RequestOptions()
                        .error(R.drawable.ic_img_error)
                        .placeholder(R.drawable.ic_img_loading))
                .into(iv)
    }
}