package com.aib.adapter

import android.os.Bundle
import com.wm.loan.R
import com.wm.loan.databinding.ItemCardDetailImgBinding
import com.aib.entity.CardDetailEntity
import com.aib.view.activity.PhotoViewActivity
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.blankj.utilcode.util.ActivityUtils

/**
 * 帖子详情内容图片区
 */
class CardDetailImgAdapter : BaseDelegateAdapter<ItemCardDetailImgBinding>() {
    private var data: CardDetailEntity? = null

    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_card_detail_img

    override fun getCount(): Int {
        if (data == null) {
            return 0
        } else {
            if (data!!.originalImageArr == null) {
                return 0
            } else {
                return data!!.originalImageArr.size
            }
        }
    }

    override fun getLayoutHelper(): LayoutHelper = LinearLayoutHelper()

    override fun bindView(binding: ItemCardDetailImgBinding, position: Int) {
        binding.entity = data!!.originalImageArr[position]
        binding.executePendingBindings()

        binding.root.setOnClickListener {
            val args = Bundle()
            args.putSerializable("data", data!!.originalImageArr)
            args.putInt("position", position)
            args.putString("content", data!!.content)
            ActivityUtils.startActivity(args, PhotoViewActivity::class.java)
        }
    }

    fun passData(data: CardDetailEntity) {
        this.data = data
    }
}