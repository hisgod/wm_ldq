package com.aib.adapter

import android.os.Bundle
import com.wm.loan.R
import com.wm.loan.databinding.ItemHomeTypeBinding
import com.aib.utils.Constants
import com.aib.entity.TypeLoanEntity
import com.aib.view.activity.LoanDetailActivity
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.blankj.utilcode.util.ActivityUtils
import java.text.DecimalFormat
import java.util.ArrayList

/**
 *
 */
class HomeTypeAdapter : BaseDelegateAdapter<ItemHomeTypeBinding>() {
    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_home_type

    override fun getCount(): Int = if (data == null) 0 else data!!.size

    override fun getLayoutHelper(): LayoutHelper = LinearLayoutHelper()

    override fun bindView(binding: ItemHomeTypeBinding, position: Int) {
        val entity = data!!.get(position)
        binding.entity = entity
        binding.tvRate.text = DecimalFormat("0.00").format(entity.loanRate * 100).toString()
        binding.executePendingBindings()

        binding.ll.setOnClickListener {
            val extras = Bundle()
            extras.putInt(Constants.LOAN_ID, entity.id)
            ActivityUtils.startActivity(extras, LoanDetailActivity::class.java)
        }
    }

    private var data: ArrayList<TypeLoanEntity>? = null

    fun setData(data: ArrayList<TypeLoanEntity>) {
        this.data = data
    }
}