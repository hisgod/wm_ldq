package com.aib.adapter

import com.wm.loan.R
import com.wm.loan.databinding.ItemCardContentBinding
import com.aib.entity.CardDetailEntity
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper

/**
 * 帖子内容
 */
class CardContentAdapter : BaseDelegateAdapter<ItemCardContentBinding>() {
    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_card_content

    override fun getCount(): Int = 1

    override fun getLayoutHelper(): LayoutHelper = SingleLayoutHelper()

    override fun bindView(binding: ItemCardContentBinding, position: Int) {
        binding.entity = data
        binding.executePendingBindings()
    }

    private var data: CardDetailEntity? = null

    fun passData(data: CardDetailEntity) {
        this.data = data
    }

}