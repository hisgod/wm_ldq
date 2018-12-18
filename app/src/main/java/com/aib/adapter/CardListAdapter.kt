package com.aib.adapter

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.wm.loan.R
import com.wm.loan.databinding.ItemCardListBinding
import com.aib.entity.CardListEntity
import com.aib.view.activity.CardDetailActivity
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.blankj.utilcode.util.ActivityUtils
import java.util.ArrayList

/**
 * 帖子列表
 */
class CardListAdapter : BaseDelegateAdapter<ItemCardListBinding>() {
    private var data: ArrayList<CardListEntity>? = null

    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_card_list

    override fun getCount(): Int = if (data == null) 0 else data!!.size

    override fun getLayoutHelper(): LayoutHelper = LinearLayoutHelper()

    override fun bindView(binding: ItemCardListBinding, position: Int) {
        val entity = data!![position]

        binding.entity = entity

        binding.executePendingBindings()

        if (!TextUtils.isEmpty(entity.tagColor)) {
            binding.tvTag.setBackgroundColor(Color.parseColor(entity.tagColor))
        }

        if (TextUtils.isEmpty(entity.thumbnail)) {
            binding.iv.visibility = View.GONE
        } else {
            binding.iv.visibility = View.VISIBLE
        }

        binding.root.setOnClickListener {
            val args = Bundle()
            args.putInt("id", entity.id)
            ActivityUtils.startActivity(args, CardDetailActivity::class.java)
        }
    }

    fun passData(data: ArrayList<CardListEntity>) {
        this.data = data
    }
}