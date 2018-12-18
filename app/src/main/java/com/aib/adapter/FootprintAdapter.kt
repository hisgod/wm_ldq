package com.aib.adapter
import android.os.Bundle
import com.aib.utils.Constants
import com.aib.entity.LoanDetailEntity
import com.aib.view.activity.LoanDetailActivity
import com.blankj.utilcode.util.ActivityUtils

import com.wm.loan.R
import com.wm.loan.databinding.ItemFootprintBinding

/**
 * 我的足迹adapter
 */
class FootprintAdapter : BaseRvAdapter<ItemFootprintBinding>() {
    private var entity: List<LoanDetailEntity>? = null

    override fun attachToParent(): Boolean = true

    override fun getResId(): Int = R.layout.item_footprint

    override fun getCount(): Int {
        if (entity == null) {
            return 0
        } else {
            return entity!!.size
        }
    }

    override fun bindView(binding: ItemFootprintBinding, position: Int) {
        val entity = this.entity!![position]
        binding.entity = entity
        binding.executePendingBindings()

        binding.ll.setOnClickListener {
            val extras = Bundle()
            extras.putInt(Constants.LOAN_ID, entity.id)
            ActivityUtils.startActivity(extras, LoanDetailActivity::class.java)
        }
    }

    fun setData(entity: List<LoanDetailEntity>?) {
        this.entity = entity
    }
}
