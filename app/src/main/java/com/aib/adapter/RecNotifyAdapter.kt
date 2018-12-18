package com.aib.adapter

import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.wm.loan.R
import com.wm.loan.databinding.ItemRecNotifyBinding

class RecNotifyAdapter : BaseDelegateAdapter<ItemRecNotifyBinding>() {
    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_rec_notify

    override fun getCount(): Int = 1

    override fun getLayoutHelper(): LayoutHelper = SingleLayoutHelper()

    override fun bindView(binding: ItemRecNotifyBinding, position: Int) {
    }
}
