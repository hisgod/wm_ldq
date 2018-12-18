package com.aib.adapter

import android.os.Bundle
import com.aib.utils.Constants
import com.aib.entity.ApplyInfoEntity
import com.aib.view.activity.LoanDetailActivity
import com.aib.view.activity.WebViewActivity
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SpanUtils

import com.wm.loan.R
import com.wm.loan.databinding.ItemApplyStatusBinding

/**
 * 申请状态广告
 */
class ApplyStatusAdapter : BaseDelegateAdapter<ItemApplyStatusBinding>() {
    var data: List<ApplyInfoEntity>? = null

    override fun attachToParent(): Boolean = true

    override fun getResId(): Int = R.layout.item_apply_status

    override fun getCount(): Int {
        if (data == null) {
            return 0
        } else {
            return data!!.size
        }
    }

    override fun getLayoutHelper(): LayoutHelper = LinearLayoutHelper()

    override fun bindView(binding: ItemApplyStatusBinding, position: Int) {
        val entity = data!![position]
        binding.entity = entity
        binding.executePendingBindings()

        val applyNum = SpanUtils()
                .append(entity.applyCountFake.toString() + "").setForegroundColor(ctx!!.resources.getColor(R.color.color_ff9600))
                .append("人已经申请")
                .create()
        binding.tvApply.text = applyNum

        binding.ll.setOnClickListener {
            if (entity.isJump == 0) {
                val args = Bundle()
                args.putInt(Constants.LOAN_ID, entity.prodId)
                ActivityUtils.startActivity(args, LoanDetailActivity::class.java)
            } else {
                val args = Bundle()
                args.putString(Constants.URL, entity.requestUrl)
                ActivityUtils.startActivity(args, WebViewActivity::class.java)
            }
        }
    }

    fun passData(data: List<ApplyInfoEntity>) {
        this.data = data
    }

}
