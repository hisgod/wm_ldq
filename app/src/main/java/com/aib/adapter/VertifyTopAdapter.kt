package com.aib.adapter

import com.wm.loan.R
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.wm.loan.databinding.ItemVertifyTopBinding

/**
 * 等待审核顶部
 */
class VertifyTopAdapter : BaseDelegateAdapter<ItemVertifyTopBinding>() {
    override fun bindView(binding: ItemVertifyTopBinding, position: Int) {
    }

    override fun attachToParent(): Boolean = true

    override fun getResId(): Int = R.layout.item_vertify_top

    override fun getCount(): Int = 1

    override fun getLayoutHelper(): SingleLayoutHelper = SingleLayoutHelper()
}