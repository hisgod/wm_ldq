package com.aib.adapter

import android.os.Bundle

import com.aib.entity.MoreLoanEntity
import com.aib.utils.Constants
import com.aib.view.activity.LoanDetailActivity
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.blankj.utilcode.util.ActivityUtils
import com.wm.loan.R
import com.wm.loan.databinding.ItemAllBinding
import java.text.DecimalFormat

/**
 * 贷款列表
 */
class LoanListAdapter : BaseDelegateAdapter<ItemAllBinding>() {

    private var data: List<MoreLoanEntity>? = null

    override fun attachToParent(): Boolean = true

    override fun getResId(): Int = R.layout.item_all

    override fun getCount(): Int = if (data == null) 0 else data!!.size

    override fun getLayoutHelper(): LayoutHelper = LinearLayoutHelper()

    override fun bindView(binding: ItemAllBinding, position: Int) {
        binding.entity = data?.get(position)
        binding.executePendingBindings()

        val loanRate = data!![position].loanRate
        binding.tvRate.text = DecimalFormat("0.00").format(loanRate * 100).toString()

        binding.ll.setOnClickListener {
            val extras = Bundle()
            extras.putInt(Constants.LOAN_ID, data!!.get(position).id)
            ActivityUtils.startActivity(extras, LoanDetailActivity::class.java)
        }
    }

    fun setData(data: List<MoreLoanEntity>) {
        this.data = data
    }
}
