package com.aib.view.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.aib.utils.Constants
import com.aib.view.activity.CreditQueryActivity
import com.aib.view.activity.LoginActivity
import com.aib.viewmodel.CreditFragmentVm
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ThreadUtils

import com.wm.loan.R
import com.wm.loan.databinding.FragmentCreditBinding

/**
 * 征信
 */
class CreditFragment : BaseFragment<FragmentCreditBinding>() {

    private var vm: CreditFragmentVm? = null

    override fun getResId(): Int = R.layout.fragment_credit

    override fun initData(savedInstanceState: Bundle?) {
        vm = getViewModel(CreditFragmentVm::class.java)
        binding.presenter = Presenter()
    }

    inner class Presenter {
        fun enterCreditQuery(view: View) {
            if (SPUtils.getInstance().getString(Constants.TOKEN) !== "") {
                activity!!.runOnUiThread {
                    vm!!.queryCredit(SPUtils.getInstance().getString(Constants.TOKEN)).observe(this@CreditFragment, Observer { queryCreditEntityBaseEntity ->
                        if (queryCreditEntityBaseEntity!!.code == 1) {
                            ThreadUtils.getIoPool().submit { }
                            val args = Bundle()
                            args.putDouble("args", queryCreditEntityBaseEntity.data.creditFee)
                            ActivityUtils.startActivity(args, CreditQueryActivity::class.java)
                        } else {
                            if (queryCreditEntityBaseEntity.msg == "请登录") {
                                ActivityUtils.startActivity(LoginActivity::class.java)
                            }
                        }
                    })
                }
            } else {
                ActivityUtils.startActivity(LoginActivity::class.java)
            }
        }
    }
}
