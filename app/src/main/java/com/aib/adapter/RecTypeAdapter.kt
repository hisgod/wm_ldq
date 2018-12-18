package com.aib.adapter

import android.os.Bundle
import com.aib.utils.Constants
import com.aib.entity.HomeEntity
import com.aib.view.activity.PayWebActivity
import com.aib.view.activity.TypeListActivity
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.GridLayoutHelper
import com.blankj.utilcode.util.ActivityUtils

import com.wm.loan.R
import com.wm.loan.databinding.ItemRecTypeBinding

class RecTypeAdapter : BaseDelegateAdapter<ItemRecTypeBinding>() {
    private var data: List<HomeEntity.AppHomeCategoryListBean>? = null

    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_rec_type

    override fun getCount(): Int = if (data == null) 0 else data!!.size

    override fun getLayoutHelper(): LayoutHelper = GridLayoutHelper(2, 2)

    override fun bindView(binding: ItemRecTypeBinding, position: Int) {
        val bean = data!![position]
        binding.entity = bean
        binding.executePendingBindings()

        binding.cv.setOnClickListener {
            val isJump = bean.isJump
            if (isJump == 0) {
                val title = bean.title
                val args = Bundle()
                args.putString("title", title)
                args.putInt("typeId", bean.id)
                args.putString("bgUrl", bean.backgroundImg)
                args.putString("bgIcon", bean.backgroundIcon)
                args.putString("des2", bean.des)
                ActivityUtils.startActivity(args, TypeListActivity::class.java)
            } else {
                val requestUrl = bean.requestUrl
                val args = Bundle()
                args.putString(Constants.URL, requestUrl)
                ActivityUtils.startActivity(args, PayWebActivity::class.java)
            }
        }
    }

    fun setData(data: List<HomeEntity.AppHomeCategoryListBean>) {
        this.data = data
    }
}
