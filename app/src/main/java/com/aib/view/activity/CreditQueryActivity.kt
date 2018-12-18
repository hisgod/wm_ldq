package com.aib.view.activity

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.TextUtils
import android.view.View

import com.aib.utils.Constants
import com.aib.viewmodel.CreditQueryVm
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.wm.loan.R
import com.wm.loan.databinding.ActivityCreditQueryBinding


/**
 * 征信查询
 */
class CreditQueryActivity : BaseActivity<ActivityCreditQueryBinding>() {

    private var vm: CreditQueryVm? = null
    private var pd: ProgressDialog? = null

    override fun getResId(): Int = R.layout.activity_credit_query

    override fun initData(savedInstanceState: Bundle?) {
        BarUtils.setStatusBarAlpha(this, 0)

        vm = getViewModel(CreditQueryVm::class.java)

        binding.presenter = Presenter()

        pd = ProgressDialog(this@CreditQueryActivity)
        pd!!.setMessage("加载中...")

        val price = intent.getDoubleExtra("args", 0.0)
        binding.price = price

        if (price != 0.0) {
            binding.btn.visibility = View.VISIBLE
        } else {
            binding.btn.visibility = View.GONE
        }

        binding.rgPay.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_alipay -> {
                }
            }
        }

        //是否同意协议
        binding.cb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.btnPay.setBackgroundColor(resources.getColor(R.color.colorAccent))
            } else {
                binding.btnPay.setBackgroundColor(resources.getColor(R.color.color_adacac))
            }
        }
    }

    inner class Presenter {
        fun query(view: View) {
            val getName = binding.etName.text.toString().trim { it <= ' ' }
            val getCid = binding.etCid.text.toString().trim { it <= ' ' }
            val getPhone = binding.etPhone.text.toString().trim { it <= ' ' }

            if (TextUtils.isEmpty(getName)) {
                ToastUtils.showShort("名字不能为空")
                return
            }

            if (TextUtils.isEmpty(getCid)) {
                ToastUtils.showShort("身份证不能为空")
                return
            }

            if (!RegexUtils.isIDCard18(getCid)) {
                ToastUtils.showShort("身份证格式不正确")
                return
            }

            if (TextUtils.isEmpty(getPhone)) {
                ToastUtils.showShort("手机号不能为空")
                return
            }

            if (!binding.cb.isChecked) {
                ToastUtils.showShort("请勾选协议")
                return
            }

            if (binding.rbAlipay.isChecked) {
                pd!!.show()
                pay(getName, getCid, getPhone)
            } else {
                ToastUtils.showShort("微信")
            }
        }

        /**
         * 查询报告
         *
         * @param view
         */
        fun queryCredit(view: View) {
            ThreadUtils.executeByIo(object : ThreadUtils.Task<String>() {
                @Throws(Throwable::class)
                override fun doInBackground(): String? {
                    return SPUtils.getInstance().getString(Constants.TOKEN)
                }

                override fun onSuccess(result: String?) {
                    if (TextUtils.isEmpty(result)) {
                        ToastUtils.showShort("token为空")
                    } else {
                        vm!!.queryCredit(result).observe(this@CreditQueryActivity, Observer { queryCreditEntityBaseEntity ->
                            if (queryCreditEntityBaseEntity!!.code == 1) {
                                val args = Bundle()
                                args.putString(Constants.URL, queryCreditEntityBaseEntity.data.creditRptLink)
                                ActivityUtils.startActivity(args, WebViewActivity::class.java)
                            } else {
                                ToastUtils.showShort("获取数据失败")
                            }
                        })
                    }
                }

                override fun onCancel() {

                }

                override fun onFail(t: Throwable) {

                }
            })
        }
    }

    /**
     * 支付金额
     *
     * @param getName
     * @param getCid
     * @param getPhone
     */
    private fun pay(getName: String, getCid: String, getPhone: String) {
        vm!!.getPayUrl(getCid, getName, getPhone).observe(this, Observer { payParamsEntityBaseEntity ->
            if (payParamsEntityBaseEntity!!.code == 1) {
                pd!!.dismiss()
                val args = Bundle()
                args.putString("URL", payParamsEntityBaseEntity.data.h5Url)
                args.putString("WEB_PARAMS", payParamsEntityBaseEntity.data.orderUrl)
                args.putString("ORDER_NUM", payParamsEntityBaseEntity.data.merchantOutOrderNo)
                ActivityUtils.startActivity(args, PayWebActivity::class.java)
            } else {
                pd!!.dismiss()
                ToastUtils.showShort("获取数据失败")
            }
        })
    }
}
