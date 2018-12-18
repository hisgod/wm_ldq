package com.aib.adapter

import com.wm.loan.R
import com.wm.loan.databinding.ItemOrderBinding
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper

/**
 * 订单适配器
 */
class OrderAdapter : BaseDelegateAdapter<ItemOrderBinding>() {
    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_order

    override fun getCount(): Int = 10

    override fun getLayoutHelper(): LayoutHelper = LinearLayoutHelper()

    override fun bindView(binding: ItemOrderBinding, position: Int) {

    }
}
