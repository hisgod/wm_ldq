package com.aib.adapter

import android.os.Bundle
import com.aib.utils.Constants
import com.aib.entity.HomeEntity
import com.aib.view.activity.LoanDetailActivity
import com.aib.view.activity.PayWebActivity
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SpanUtils
import com.wm.loan.R
import com.wm.loan.databinding.ItemRecProductBinding
import java.util.ArrayList


class RecProductAdapter : BaseDelegateAdapter<ItemRecProductBinding>() {

    private var data: List<HomeEntity.AppHomeProdlistListBean>? = null

    override fun attachToParent(): Boolean = true

    override fun getResId(): Int = R.layout.item_rec_product

    override fun getCount(): Int = if (data == null) 0 else data!!.size

    override fun getLayoutHelper(): LayoutHelper = LinearLayoutHelper()

    override fun bindView(binding: ItemRecProductBinding, position: Int) {
        val bean = data!![position]
        binding.entity = bean
        binding.executePendingBindings()

        val applyNum = SpanUtils()
                .append(bean.applyCountFake.toString() + "").setForegroundColor(ctx!!.resources.getColor(R.color.color_ff9600))
                .append("人已经申请")
                .create()
        binding.tvApply.text = applyNum

        binding.ll.setOnClickListener {
            if (bean.isJump == 0) {
                val args = Bundle()
                args.putInt(Constants.LOAN_ID, bean.prodId)
                ActivityUtils.startActivity(args, LoanDetailActivity::class.java)
            } else {
                val args = Bundle()
                args.putString(Constants.URL, bean.requestUrl)
                ActivityUtils.startActivity(args, PayWebActivity::class.java)
            }
        }
    }

    fun setData(data: ArrayList<HomeEntity.AppHomeProdlistListBean>) {
        this.data = data
    }
}