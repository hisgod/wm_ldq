package com.aib.view.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.aib.utils.RetryCallback
import com.aib.net.Status
import com.aib.viewmodel.AgreementVm
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.wm.loan.R
import com.wm.loan.databinding.ActivityAgreementBinding


/**
 * 协议
 */
class AgreementActivity : BaseActivity<ActivityAgreementBinding>() {
    private lateinit var vm: AgreementVm
    private var mExitTime: Long = 0

    override fun getResId(): Int = R.layout.activity_agreement

    override fun initData(savedInstanceState: Bundle?) {
        val agreementType = intent.getStringExtra("AGREEMENT_TYPE")

        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        vm = getViewModel(AgreementVm::class.java)

        binding.presenter = Presenter()

        getAgreementJson(agreementType, true)

        binding.retry = object : RetryCallback {
            override fun retryClick() {
                getAgreementJson(agreementType, false)
            }
        }
    }

    /**
     * 获取协议数据
     *
     * @param agreementType
     */
    private fun getAgreementJson(agreementType: String, isFirst: Boolean) {
        vm.getAgreementJson(agreementType, isFirst).observe(this, Observer {
            binding.resource = it
            if (it!!.status == Status.SUCCESS) binding.entity = it.data
        })
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }

        fun scrollToTop(view: View) {
            if (System.currentTimeMillis() - mExitTime > 1000) {
                mExitTime = System.currentTimeMillis()
            } else {
                //双击后执行
                binding.nsv.smoothScrollTo(0, 0)
            }
        }
    }
}
