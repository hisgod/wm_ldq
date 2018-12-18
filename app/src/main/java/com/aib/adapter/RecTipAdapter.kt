package com.aib.adapter

import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.StickyLayoutHelper
import com.wm.loan.R
import com.wm.loan.databinding.ItemRecTipBinding

class RecTipAdapter : BaseDelegateAdapter<ItemRecTipBinding>() {
    override fun attachToParent() = false

    override fun getResId(): Int = R.layout.item_rec_tip

    override fun getCount(): Int = 1

    override fun getLayoutHelper(): LayoutHelper = StickyLayoutHelper()

    override fun bindView(binding: ItemRecTipBinding, position: Int) {

    }
}
