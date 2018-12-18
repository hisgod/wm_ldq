package com.aib.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import com.wm.loan.R

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.chrisbanes.photoview.PhotoView

class PhotoViewAdapter(private val images: List<Any>?) : PagerAdapter() {

    override fun getCount(): Int {
        return images?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val iv = PhotoView(container.context)
        Glide.with(container.context).load(images!!.get(position)).apply(RequestOptions().error(R.drawable.ic_img_error).placeholder(R.drawable.ic_img_loading)).into(iv)
        container.addView(iv)
        return iv
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
