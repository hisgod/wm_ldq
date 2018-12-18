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
import com.aib.viewmodel.ForgetPwdVm
import com.blankj.utilcode.util.*
import com.wm.loan.R
import com.wm.loan.databinding.ActivityForgetPwdBinding


/**
 * 忘记密码
 */
class ForgetPwdActivity : BaseActivity<ActivityForgetPwdBinding>() {
    private var vm: ForgetPwdVm? = null
    private var dialog: ProgressDialog? = null
    private var smsToken: String? = null
    private var timer: CountDownTimer? = null

    override fun getResId(): Int = R.layout.activity_forget_pwd

    override fun initData(savedInstanceState: Bundle?) {
        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        vm = getViewModel(ForgetPwdVm::class.java)

        binding.presenter = Presenter()

        dialog = ProgressDialog(this@ForgetPwdActivity)

        /**
         * 手机号输入监听
         */
        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (RegexUtils.isMobileExact(s)) {
                    binding.btnCode.isEnabled = true
                    KeyboardUtils.hideSoftInput(this@ForgetPwdActivity) //隐藏键盘
                } else {
                    binding.btnCode.isEnabled = false
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
                    KeyboardUtils.hideSoftInput(this@ForgetPwdActivity)
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        /**
         * 发送验证码
         */
        binding.btnCode.setOnClickListener {
            dialog!!.setMessage("请求数据...")
            dialog!!.show()
            val getPhone = binding.etPhone.text.toString().trim { it <= ' ' }
            vm!!.getVertification(getPhone).observe(this@ForgetPwdActivity, Observer { stringBaseEntity ->
                if (stringBaseEntity!!.code == 1) {
                    dialog!!.dismiss()
                    smsToken = stringBaseEntity.data
                    binding.btnCode.isEnabled = false
                    timer = object : CountDownTimer(60000, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            binding.btnCode.text = (millisUntilFinished / 1000).toString() + ""
                        }

                        override fun onFinish() {
                            binding.btnCode.isEnabled = true
                            binding.btnCode.text = "发送验证码"
                        }
                    }.start()
                }
            })
        }

        /**
         * 完成
         */
        binding.btnComplete.setOnClickListener(View.OnClickListener { v ->
            val getPhone = binding.etPhone.text.toString().trim { it <= ' ' }
            val getCode = binding.etCode.text.toString().trim { it <= ' ' }
            val getPwd = binding.etNewPwd.text.toString().trim { it <= ' ' }
            val getAgainPwd = binding.etAgainPwd.text.toString().trim { it <= ' ' }

            if (TextUtils.isEmpty(getPhone)) {
                SnackbarUtils.with(v).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(resources.getColor(android.R.color.white)).setMessage("请输入号码").show()
                return@OnClickListener
            }

            if (TextUtils.isEmpty(getCode)) {
                SnackbarUtils.with(v).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(resources.getColor(android.R.color.white)).setMessage("请输入验证码").show()
                return@OnClickListener
            }

            if (TextUtils.isEmpty(getPwd)) {
                SnackbarUtils.with(v).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(resources.getColor(android.R.color.white)).setMessage("请输入密码").show()
                return@OnClickListener
            }

            if (TextUtils.isEmpty(getAgainPwd)) {
                SnackbarUtils.with(v).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(resources.getColor(android.R.color.white)).setMessage("请再一次输入密码").show()
                return@OnClickListener
            }

            if (getPwd != getAgainPwd) {
                SnackbarUtils.with(v).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(resources.getColor(android.R.color.white)).setMessage("2次输入密码不一致").show()
                return@OnClickListener
            }

            dialog!!.setMessage("请求中...")
            dialog!!.show()

            vm!!.modifyPwd(getPhone, CommonFun.md5(getPwd), getCode, smsToken).observe(this@ForgetPwdActivity, Observer { stringBaseEntity ->
                if (stringBaseEntity!!.code == 1) {
                    dialog!!.dismiss()
                    ToastUtils.showShort(stringBaseEntity.msg)
                    finish()
                }
            })
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer!!.cancel()
        }
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }
    }
}
