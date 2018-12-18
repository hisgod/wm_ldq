package com.aib.adapter

import com.wm.loan.R
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.wm.loan.databinding.ItemVertifyCenterBinding

/**
 * 等待审核中间
 */
class VertifyCenterAdapter : BaseDelegateAdapter<ItemVertifyCenterBinding>() {
    override fun bindView(binding: ItemVertifyCenterBinding, position: Int) {
    }

    override fun attachToParent(): Boolean = true

    override fun getResId(): Int = R.layout.item_vertify_center

    override fun getCount(): Int = 1

    override fun getLayoutHelper(): SingleLayoutHelper = SingleLayoutHelper()
}