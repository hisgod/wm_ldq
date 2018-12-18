package com.aib.adapter

import android.os.Bundle

import com.aib.utils.Constants
import com.aib.entity.HomeEntity
import com.aib.view.activity.LoanDetailActivity
import com.aib.view.activity.PayWebActivity
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.blankj.utilcode.util.ActivityUtils

import com.wm.loan.R
import com.wm.loan.databinding.ItemRecNowBinding

class RecNowAdapter : BaseDelegateAdapter<ItemRecNowBinding>() {
    private var homeEntity: HomeEntity? = null

    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_rec_now

    override fun getCount(): Int = 1

    override fun getLayoutHelper(): LayoutHelper = SingleLayoutHelper()

    override fun bindView(binding: ItemRecNowBinding, position: Int) {
        if (homeEntity != null) {
            binding.tvRecNow.text = homeEntity!!.appHomeToday!!.prodName + "\n" + homeEntity!!.appHomeToday!!.des

            binding.tvRecNow.setOnClickListener {
                if (homeEntity!!.appHomeToday!!.isJump == 0) {
                    val args = Bundle()
                    args.putInt(Constants.LOAN_ID, homeEntity!!.appHomeToday!!.prodId)
                    ActivityUtils.startActivity(args, LoanDetailActivity::class.java)
                } else {
                    val args = Bundle()
                    args.putString(Constants.URL, homeEntity!!.appHomeToday!!.requestUrl)
                    ActivityUtils.startActivity(args, PayWebActivity::class.java)
                }
            }
        }
    }

    fun setData(homeEntity: HomeEntity) {
        this.homeEntity = homeEntity
    }
}
