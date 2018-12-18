package com.aib.view.activity

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.os.CountDownTimer
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.aib.utils.Constants
import com.aib.viewmodel.SplashVm
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils

import com.aib.entity.BaseEntity
import com.aib.entity.UserEntity
import com.aib.net.Resource
import com.wm.loan.R
import com.wm.loan.databinding.ActivitySplashBinding
import kotlin.collections.ArrayList

/**
 * 闪屏页
 */
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private var timer: CountDownTimer? = null
    private lateinit var vm: SplashVm
    private val READ_EXTERNAL_STORAGE: String = "android.permission.READ_EXTERNAL_STORAGE"
    private var WRITE_EXTERNAL_STORAGE: String = "android.permission.WRITE_EXTERNAL_STORAGE"
    private var READ_CONTACTS: String = "android.permission.READ_CONTACTS"
    private var WRITE_CONTACTS: String = "android.permission.WRITE_CONTACTS"

    override fun getResId(): Int =R.layout.activity_splash

    @SuppressLint("WrongConstant")
    override fun initData(savedInstanceState: Bundle?) {

        binding.presenter = Presenter()

        vm = getViewModel(SplashVm::class.java)

        PermissionUtils.permission(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, READ_CONTACTS, WRITE_CONTACTS)
                .callback(object : PermissionUtils.SimpleCallback {
                    override fun onGranted() {
                        binding.tvSkip.visibility = View.VISIBLE
                        timer = object : CountDownTimer(3000, 1000) {
                            override fun onTick(l: Long) {

                            }

                            override fun onFinish() {
                                enterNext()
                            }
                        }.start()
                    }

                    override fun onDenied() {
                        ToastUtils.showShort("用户拒绝授权")
                        finish()
                    }
                }).request()
    }


    /**
     * 进入下一个页面
     */
    private fun enterNext() {
        ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<ArrayList<Any>>() {
            override fun onSuccess(result: ArrayList<Any>?) {
                if (TextUtils.isEmpty(result!!.get(1).toString())) {
                    if (result.get(0) as Boolean) {
                        ActivityUtils.startActivity(MainActivity::class.java)
                        finish()
                    } else {
                        ActivityUtils.startActivity(GuideActivity::class.java)
                        finish()
                    }
                } else {
                    isUserLogin()
                }
            }

            override fun doInBackground(): ArrayList<Any>? {
                val isFirst = SPUtils.getInstance().getBoolean(Constants.IS_FIRST)
                val localToken = SPUtils.getInstance().getString(Constants.TOKEN)
                val data = arrayListOf(isFirst, localToken)
                return data
            }
        })
    }

    /**
     * 用户免登录
     */
    private fun isUserLogin() {
        ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<String>() {
            override fun onSuccess(result: String?) {
                vm.loginFree(result).observe(this@SplashActivity, Observer {
                    parseJson(it, it!!.data!!)
                })
            }

            override fun doInBackground(): String? {
                val localToken = SPUtils.getInstance().getString(Constants.TOKEN)
                return localToken
            }
        })
    }

    /**
     * 解析数据
     */
    private fun parseJson(it: Resource<BaseEntity<UserEntity>>?, data: BaseEntity<UserEntity>) {
        if (it?.data?.code == 1) {
            ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<Boolean>() {
                @Throws(Throwable::class)
                override fun doInBackground(): Boolean? {
                    val data = data.data
                    SPUtils.getInstance().put(Constants.TOKEN, data.token)   //token
                    SPUtils.getInstance().put(Constants.TOKEN_SCRET, data.tokenSecret)   //tokenScret
                    SPUtils.getInstance().put(Constants.PHONE, data.phone)    //phone
                    SPUtils.getInstance().put(Constants.HEAD_IMG, data.headImg)//头像
                    SPUtils.getInstance().put(Constants.USER_ID, data.userId)//ID
                    return true
                }

                override fun onSuccess(result: Boolean?) {
                    if (result!!) {
                        ActivityUtils.startActivity(MainActivity::class.java)
                        finish()
                    }
                }
            })
        } else {
            ActivityUtils.startActivity(LoginActivity::class.java)
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (timer != null) {
            timer!!.cancel()
        }
    }

    /**
     * 事件控制器
     */
    inner class Presenter {
        /**
         * 跳过广告
         *
         * @param view
         */
        fun skip(view: View) {
            if (timer != null) {
                timer!!.cancel()
            }
            enterNext()
        }
    }
}