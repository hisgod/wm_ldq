package com.aib.view.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.wm.loan.R

import com.wm.loan.databinding.ActivityRepaymentStatusBinding

/**
 * 还款状态页面
 */
class RepaymentStatusActivity : BaseActivity<ActivityRepaymentStatusBinding>() {

    override fun getResId(): Int = R.layout.activity_repayment_status

    override fun initData(savedInstanceState: Bundle?) {

    }

    inner class Presenter {

    }
}
