package com.aib.view.activity

import android.os.Bundle
import android.view.View
import com.wm.loan.R


import com.wm.loan.databinding.ActivityRepaymentBinding

/**
 * 立即还款
 */
class RepaymentActivity : BaseActivity<ActivityRepaymentBinding>() {

    override fun getResId(): Int=R.layout.activity_repayment

    override fun initData(savedInstanceState: Bundle?) {
        binding.presenter=Presenter()
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }
    }
}
