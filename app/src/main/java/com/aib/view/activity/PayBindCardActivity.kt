package com.aib.view.activity

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.util.ArrayMap
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.aib.net.ApiService
import com.aib.utils.Constants
import com.aib.viewmodel.PayBindCardViewModel
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.wm.loan.R

import com.wm.loan.databinding.ActivityPayBindCardBinding

/**
 * 支付绑卡费用
 */
class PayBindCardActivity : BaseActivity<ActivityPayBindCardBinding>() {
    lateinit var vm: PayBindCardViewModel
    private var payType = 1 //支付类型

    override fun getResId(): Int = R.layout.activity_pay_bind_card

    override fun initData(savedInstanceState: Bundle?) {
        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        vm = getViewModel(PayBindCardViewModel::class.java)

        vm.getBindCardConst().observe(this, Observer {
            binding.tvMoney.text = it!!.data
        })

        binding.rg.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_alipay -> {
                    payType = 1
                }
                R.id.rb_band -> {
                    payType = 3
                }
            }
        }

        binding.presenter = Presenter()
    }

    /**
     * 支付金额
     */
    fun payMoney(type: Int) {
        when (type) {
            1 -> {
                //支付宝
                vm.bindCardByAlipay(type, SPUtils.getInstance().getString(Constants.TOKEN)).observe(this@PayBindCardActivity, Observer {
                    if (it!!.code == 1) {
                        //支付宝
                        val data = it.data
                        val extr = Bundle()
                        extr.putString("URL", data.h5Url)
                        extr.putString("WEB_PARAMS", data.url)
                        extr.putString("ORDER_NUM", data.merchantOutOrderNo)
                        ActivityUtils.startActivity(extr, PayWebActivity::class.java)
                    } else {
                        ToastUtils.showShort(it.msg)
                    }
                })
            }
            3 -> {
                //银行卡
                vm.bindCardByBank(type, SPUtils.getInstance().getString(Constants.TOKEN)).observe(this@PayBindCardActivity, Observer {
                    if (it!!.code == 1) {
                        //银行卡支付
                        val builder = AlertDialog.Builder(this@PayBindCardActivity)
                        builder.setCancelable(false)
                        builder.setView(R.layout.dialog_input_validate)
                        val dialog = builder.create()
                        dialog.show()
                        val et = dialog.findViewById<EditText>(R.id.et)
                        val btnYes = dialog.findViewById<Button>(R.id.btn_yes)
                        btnYes.setOnClickListener { view ->
                            val getValidate = et.text.toString().trim()
                            if (TextUtils.isEmpty(getValidate)) {
                                ToastUtils.showShort("请输入验证码")
                                return@setOnClickListener
                            }

                            val params = ArrayMap<String, Any>()
                            params.put("requestno", it.data)
                            params.put("token", SPUtils.getInstance().getString(Constants.TOKEN))
                            params.put("validatecode", getValidate)
                            vm.bindCardSms(params).observe(this@PayBindCardActivity, Observer {
                                if (it!!.code == 1) {
                                    ToastUtils.showShort(it.msg)
                                } else {
                                    ToastUtils.showShort(it.msg)
                                }
                                finish()
                            })
                        }
                        val btnNo = dialog.findViewById<Button>(R.id.btn_no)
                        btnNo.setOnClickListener {
                            dialog.dismiss()
                        }
                    } else {
                        ToastUtils.showShort(it.msg)
                    }
                })
            }
        }
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }

        /**
         * 确认支付
         */
        fun confirmPay(view: View) {
            payMoney(payType)
        }
    }
}
