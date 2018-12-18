package com.aib.adapter

import com.aib.entity.BandCardListEntity
import com.aib.entity.BaseEntity
import com.wm.loan.R
import com.wm.loan.databinding.ItemBandBinding
import java.util.ArrayList

class BandListAdapter(it: BaseEntity<ArrayList<BandCardListEntity>>) : BaseRvAdapter<ItemBandBinding>() {
    var it = it

    private var onItemClickListener: OnItemClickListener? = null

    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_band

    override fun getCount(): Int = if (it != null) it.data.size else 0

    override fun bindView(binding: ItemBandBinding, position: Int) {
        val entity = it.data.get(position)
        binding.tv.text = entity.cnName

        if (onItemClickListener != null) {
            binding.root.setOnClickListener {
                onItemClickListener!!.onItemClick(entity)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(itemEntity: BandCardListEntity)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }
}