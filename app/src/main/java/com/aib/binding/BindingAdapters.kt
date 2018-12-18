package com.aib.binding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wm.loan.R

object BindingAdapters {
    /**
     * 加载图片
     *
     * @param iv
     * @param url
     */
    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImge(iv: ImageView, url: Any?) {
        Glide.with(iv.context).load(url).apply(RequestOptions().error(R.drawable.ic_img_error).placeholder(R.drawable.ic_img_loading)).into(iv)
    }

    @JvmStatic
    @BindingAdapter("loadRoundImage")
    fun loadRoundImage(iv: ImageView, url: Any?) {
        Glide.with(iv.context).load(url).apply(RequestOptions().circleCrop().error(R.drawable.ic_img_error).placeholder(R.drawable.ic_img_loading)).into(iv)
    }
}
