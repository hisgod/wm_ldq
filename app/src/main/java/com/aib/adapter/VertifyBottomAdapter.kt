package com.aib.adapter

import com.wm.loan.R
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.wm.loan.databinding.ItemVertifyBottomBinding

class VertifyBottomAdapter : BaseDelegateAdapter<ItemVertifyBottomBinding>() {
    override fun bindView(binding: ItemVertifyBottomBinding, position: Int) {
    }

    override fun attachToParent(): Boolean = true

    override fun getResId(): Int = R.layout.item_vertify_bottom

    override fun getCount(): Int = 10

    override fun getLayoutHelper(): LinearLayoutHelper = LinearLayoutHelper()
}