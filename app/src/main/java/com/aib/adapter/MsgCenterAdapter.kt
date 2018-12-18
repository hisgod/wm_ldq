package com.aib.adapter
import com.aib.entity.MsgCenterEntity
import com.wm.loan.R
import com.wm.loan.databinding.ItemMsgCenterBinding

class MsgCenterAdapter : BaseRvAdapter<ItemMsgCenterBinding>() {
    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_msg_center

    override fun getCount(): Int = if (data == null) 0 else data!!.size

    override fun bindView(binding: ItemMsgCenterBinding, position: Int) {
        val listBean = data!![position]
        binding.entity = listBean
        binding.executePendingBindings()
    }

    private var data: List<MsgCenterEntity.ListBean>? = null

    fun setData(data: List<MsgCenterEntity.ListBean>) {
        this.data = data
    }
}
