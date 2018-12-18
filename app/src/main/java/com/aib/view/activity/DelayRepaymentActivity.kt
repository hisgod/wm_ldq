package com.aib.view.activity

import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.view.View
import android.widget.Button
import com.wm.loan.R
import com.wm.loan.databinding.ActivityDelayRepaymentBinding


/**
 * 延期还款
 */
class DelayRepaymentActivity : BaseActivity<ActivityDelayRepaymentBinding>() {

    override fun getResId(): Int = R.layout.activity_delay_repayment

    override fun initData(savedInstanceState: Bundle?) {
        binding.presenter = Presenter()
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }

        /**
         * 立即支付
         */
        fun pay(view: View) {
            val dialog = BottomSheetDialog(this@DelayRepaymentActivity)
            dialog.setContentView(R.layout.dialog_choice_pay_way)
            dialog.show()

            val btnPay = dialog.findViewById<Button>(R.id.btn_pay)
            btnPay!!.setOnClickListener {

            }
        }
    }
}
