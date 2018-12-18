package com.aib.view.activity

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.TextUtils
import android.view.View

import com.aib.utils.CommonFun
import com.aib.entity.UserEntity
import com.aib.utils.Constants
import com.aib.viewmodel.LoginVm


import cn.jpush.android.api.JPushInterface
import com.blankj.utilcode.util.*
import com.wm.loan.R
import com.wm.loan.databinding.ActivityLoginBinding

/**
 * 登录
 */
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private lateinit var vm: LoginVm
    private var dialog: ProgressDialog? = null

    override fun getResId(): Int = R.layout.activity_login

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        vm = getViewModel(LoginVm::class.java)

        binding.presenter = Presenter()
    }

    /**
     * 获取登录返回数据
     *
     * @param getPhone
     * @param getPwd
     */
    private fun getLoginJson(getPhone: String, getPwd: String) {
        vm.login(getPhone, CommonFun.md5(getPwd)).observe(this@LoginActivity, Observer { loginEntityBaseEntity ->
            if (loginEntityBaseEntity!!.code == 1) {
                JPushInterface.setAlias(applicationContext, 0, loginEntityBaseEntity.data.phone)

                val data = loginEntityBaseEntity.data

                saveData(data)
            } else {
                dialog!!.dismiss()
                ToastUtils.showShort(loginEntityBaseEntity.msg)
                if (loginEntityBaseEntity.msg == "账号未注册") {
                    ActivityUtils.startActivity(RegisterActivity::class.java)
                }
            }
            KeyboardUtils.hideSoftInput(this@LoginActivity)
        })
    }

    /**
     * 保存数据到本地
     *
     * @param data
     */
    private fun saveData(data: UserEntity) {
        ThreadUtils.executeByIo(object : ThreadUtils.Task<Boolean>() {
            @Throws(Throwable::class)
            override fun doInBackground(): Boolean? {
                SPUtils.getInstance().put(Constants.TOKEN, data.token)   //token
                SPUtils.getInstance().put(Constants.TOKEN_SCRET, data.tokenSecret)   //tokenScret
                SPUtils.getInstance().put(Constants.PHONE, data.phone)    //phone
                SPUtils.getInstance().put(Constants.HEAD_IMG, data.headImg)//头像
                SPUtils.getInstance().put(Constants.USER_ID, data.userId)//ID
                return true
            }

            override fun onSuccess(result: Boolean?) {
                if (result!!) {
                    dialog!!.dismiss()
                    ActivityUtils.startActivity(MainActivity::class.java)
                    finish()
                }
            }

            override fun onCancel() {

            }

            override fun onFail(t: Throwable) {
                ToastUtils.showShort(t.message)
            }
        })
    }

    inner class Presenter {

        fun back(view: View) {
            finish()
        }

        /**
         * 注册
         *
         * @param view
         */
        fun register(view: View) {
            ActivityUtils.startActivity(RegisterActivity::class.java)
        }

        /**
         * 忘记密码
         *
         * @param view
         */
        fun modifyPwd(view: View) {
            ActivityUtils.startActivity(ForgetPwdActivity::class.java)
        }

        /**
         * 手机号码输入EditText
         *
         * @param s
         * @param start
         * @param before
         * @param count
         */
        fun phoneEditTextChange(s: CharSequence, start: Int, before: Int, count: Int) {
            if (s.length == 11) {
                KeyboardUtils.hideSoftInput(this@LoginActivity)
                KeyboardUtils.showSoftInput(binding.etPwd)
            }
        }

        /**
         * 登录
         *
         * @param view
         */
        fun login(view: View) {
            val getPhone = binding.etPhone.text.toString().trim()
            val getPwd = binding.etPwd.text.toString().trim()

            if (TextUtils.isEmpty(getPhone)) {
                SnackbarUtils.with(view).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(resources.getColor(android.R.color.white)).setMessage("请输入手机号").show()
                return
            }

            if (!RegexUtils.isMobileExact(getPhone)) {
                SnackbarUtils.with(view).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(resources.getColor(android.R.color.white)).setMessage("请输入正确手机号").show()
                return
            }

            if (TextUtils.isEmpty(getPwd)) {
                SnackbarUtils.with(view).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(resources.getColor(android.R.color.white)).setMessage("请输入密码").show()
                return
            }

            KeyboardUtils.hideSoftInput(this@LoginActivity)
            dialog = ProgressDialog(this@LoginActivity)
            dialog!!.setMessage("登陆中...")
            dialog!!.show()
            getLoginJson(getPhone, getPwd)
        }
    }
}
