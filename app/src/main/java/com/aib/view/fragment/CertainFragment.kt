package com.aib.view.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View

import com.aib.utils.Constants
import com.aib.viewmodel.CertainFragmentVm
import com.authreal.api.AuthBuilder
import com.authreal.api.OnResultListener

import com.wm.loan.R
import com.wm.loan.databinding.FragmentCertainBinding

import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.aib.entity.IdCardEntity
import com.aib.sdk.bqs.BqsVertification
import com.aib.view.activity.*
import com.blankj.utilcode.util.*
import com.bqs.crawler.cloud.sdk.OnLoginResultListener
import com.google.gson.Gson
import java.util.*

/**
 * 一定下款
 */
class CertainFragment : BaseFragment<FragmentCertainBinding>() {

    private lateinit var vm: CertainFragmentVm

    override fun getResId(): Int = R.layout.fragment_certain

    override fun initData(savedInstanceState: Bundle?) {

        vm = getViewModel(CertainFragmentVm::class.java)

        binding.presenter = Presenter()

    }

    /**
     * 获取用户下一个步骤
     */
    private fun getNextStatus(result: String?) {
        vm.nextStep(result!!).observe(this, Observer {
            if (it!!.code == 1) {
                when (it.data.nextStep) {
                    "Identity" -> {
                        //身份认证
                        faceAuth()
                    }
                    "Contacts" -> {
                        //联系人认证
                        ActivityUtils.startActivity(RelationActivity::class.java)
                    }
                    "Operators" -> {
                        //运营商认证
                        BqsVertification.switchVertify("黄廉飘", "440881199508065935", "15360060187", this@CertainFragment.activity as AppCompatActivity, Constants.OPERATOR_TYPE, object : OnLoginResultListener {
                            override fun onLoginSuccess(p0: Int) {
                                vm.postOperator(result).observe(this@CertainFragment, Observer {
                                    if (it!!.code == 1) {
                                        ToastUtils.showShort("授权成功 serviceId=$p0")
                                    }
                                })
                            }

                            override fun onLoginFailure(p0: String?, p1: String?, p2: Int) {
                                //serviceId为服务类型
                                ToastUtils.showShort(String.format(Locale.CHINA, "授权失败 resultCode=%s ,resultDesc=%s,  serviceId=%d", p0, p1, p2))
                            }
                        })
                    }
                    "BindBankCard" -> {
                        //绑定银行卡
                        ActivityUtils.startActivity(BindCardActivity::class.java)
                    }
                    "AccessFee" -> {
                        //缴纳认证费
                        ActivityUtils.startActivity(PayBindCardActivity::class.java)
                    }
                    "Zhima" -> {
                        //芝麻认证
                        BqsVertification.switchVertify("黄廉飘", "440881199508065935", "15360060187", this@CertainFragment.activity as AppCompatActivity, Constants.ZM, object : OnLoginResultListener {
                            override fun onLoginSuccess(p0: Int) {
                                ToastUtils.showShort("授权成功 serviceId=$p0")
                            }

                            override fun onLoginFailure(p0: String?, p1: String?, p2: Int) {
                                //serviceId为服务类型
                                ToastUtils.showShort(String.format(Locale.CHINA, "授权失败 resultCode=%s ,resultDesc=%s,  serviceId=%d", p0, p1, p2))
                            }
                        })
                    }
                    "Work" -> {
                        //工作认证
                        ActivityUtils.startActivity(WorkInfoActivity::class.java)
                    }
                    "Financial" -> {
                        //金融信息认证
                        ActivityUtils.startActivity(FinanceInfoActivity::class.java)
                    }
                }
            }
        })
    }


    inner class Presenter {
        /**
         * 立即申请
         *
         * @param view
         */
        fun apply(view: View) {
//            ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<String>() {
//                override fun onSuccess(result: String?) {
//                    if (result != "") {
//                        getNextStatus(result)
//                    } else {
//                        ActivityUtils.startActivity(LoginActivity::class.java)
//                    }
//                }
//
//                override fun doInBackground(): String? = SPUtils.getInstance().getString(Constants.TOKEN)
//            })

//            ActivityUtils.startActivity(PayBindCardActivity::class.java)

        }
    }

    /**
     * 身份证认证
     */
    private fun faceAuth() {
        val id = "demo_" + Date().time
        val mAuthBuilder = AuthBuilder(id, Constants.ID_AUTH_KEY, "", OnResultListener {
            val idCardEntity = Gson().fromJson(it, IdCardEntity::class.java)
            ThreadUtils.getIoPool().submit({
                SPUtils.getInstance().put(Constants.USER_NAME, idCardEntity.id_name)
                SPUtils.getInstance().put(Constants.USER_ID_CARD, idCardEntity.id_no)
            })
        })
        mAuthBuilder.faceAuth(ctx)
    }
}
