package com.aib.view.activity

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View

import com.aib.utils.CommonFun
import com.aib.viewmodel.RegisterVm
import com.blankj.utilcode.util.*
import com.wm.loan.R
import com.wm.loan.databinding.ActivityRegisterBinding

/**
 * 注册
 */
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {
    private var dialog: ProgressDialog? = null
    private var smsToken: String? = null    //短信验证码返回的值
    private var timer: CountDownTimer? = null
    private lateinit var vm: RegisterVm

    override fun getResId(): Int = R.layout.activity_register

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        vm = getViewModel(RegisterVm::class.java)

        binding.presenter = Presenter()

        dialog = ProgressDialog(this@RegisterActivity)

        /**
         * 监听手机号输入
         */
        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (RegexUtils.isMobileExact(s)) {
                    binding.tvCode.isEnabled = true
                    KeyboardUtils.hideSoftInput(this@RegisterActivity) //隐藏键盘
                } else {
                    binding.tvCode.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        /**
         * 监听验证码输入
         */
        binding.etCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length == 5) {
                    KeyboardUtils.hideSoftInput(this@RegisterActivity)
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })


        /**
         * 发送验证码
         */
        binding.tvCode.setOnClickListener {
            dialog!!.setMessage("请求数据...")
            dialog!!.show()
            val getPhone = binding.etPhone.text.toString().trim { it <= ' ' }
            sendVertificationCode(getPhone)
        }

        /**
         * 注册
         */
        binding.btnRegister.setOnClickListener(View.OnClickListener { v ->
            val getPhone = binding.etPhone.text.toString().trim { it <= ' ' }
            val getCode = binding.etCode.text.toString().trim { it <= ' ' }
            val getPwd = binding.etPwd.text.toString().trim { it <= ' ' }

            if (TextUtils.isEmpty(getPhone)) {
                SnackbarUtils.with(v).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(resources.getColor(android.R.color.white)).setMessage("请填写号码").show()
                return@OnClickListener
            }

            if (TextUtils.isEmpty(getCode)) {
                SnackbarUtils.with(v).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(resources.getColor(android.R.color.white)).setMessage("请填写验证码").show()
                return@OnClickListener
            }

            if (TextUtils.isEmpty(getPwd)) {
                SnackbarUtils.with(v).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(resources.getColor(android.R.color.white)).setMessage("请填写密码").show()
                return@OnClickListener
            }

            dialog!!.setMessage("请求中...")
            dialog!!.show()
            val channelId = applicationContext.resources.getInteger(R.integer.channelId)
            vm.register(getPhone, CommonFun.md5(getPwd), getCode, smsToken, channelId).observe(this@RegisterActivity, Observer { stringBaseEntity ->
                if (stringBaseEntity!!.code == 1) {
                    dialog!!.dismiss()
                    ToastUtils.showShort(stringBaseEntity.msg)
                    finish()
                } else {
                    ToastUtils.showShort(stringBaseEntity.msg)
                    finish()
                }
            })
        })

        /**
         * 进入协议页面
         */
        binding.tvAgreement.setOnClickListener {
            val args = Bundle()
            args.putString("AGREEMENT_TYPE", "AGREEMENT_AUTHORIZE")
            ActivityUtils.startActivity(args, AgreementActivity::class.java)
        }
    }

    /**
     * 发送验证码
     *
     * @param getPhone
     */
    private fun sendVertificationCode(getPhone: String) {
        vm.getVertification(getPhone).observe(this@RegisterActivity, Observer { stringBaseEntity ->
            if (stringBaseEntity!!.code == 1) {
                dialog!!.dismiss()
                smsToken = stringBaseEntity.data
                binding.tvCode.isEnabled = false
                timer = object : CountDownTimer(60000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        binding.tvCode.text = (millisUntilFinished / 1000).toString() + ""
                    }

                    override fun onFinish() {
                        binding.tvCode.isEnabled = true
                        binding.tvCode.text = "发送验证码"
                    }
                }.start()
            } else {
                dialog!!.dismiss()
                ToastUtils.showShort(stringBaseEntity.msg)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer!!.cancel()
        }
    }

    inner class Presenter {
        /**
         * 退出
         */
        fun back() {
            finish()
        }
    }
}