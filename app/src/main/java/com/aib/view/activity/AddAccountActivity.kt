package com.aib.view.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.aib.entity.AccountEntity
import com.aib.viewmodel.AccountViewModel
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.wm.loan.R
import com.wm.loan.databinding.ActivityAddAccountBinding

/**
 *  * 华为渠道包
 *
 * 添加账户
 */
class AddAccountActivity : BaseActivity<ActivityAddAccountBinding>() {
    private lateinit var vm: AccountViewModel

    override fun getResId(): Int = R.layout.activity_add_account

    override fun initData(savedInstanceState: Bundle?) {
        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        binding.presenter = Presenter()
        vm = getViewModel(AccountViewModel::class.java)
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }

        fun save(view: View) {
            val getBankName = binding.etBankName.text.toString().trim()
            val getCardId = binding.etCardId.text.toString().trim()
            val getMoney = binding.etMoney.text.toString().trim()

            if (TextUtils.isEmpty(getBankName)) {
                ToastUtils.showShort("请输入账户名")
                return
            }
            if (TextUtils.isEmpty(getCardId)) {
                ToastUtils.showShort("请输入账户号")
                return
            }
            if (TextUtils.isEmpty(getMoney)) {
                ToastUtils.showShort("请输入金额")
                return
            }

            vm.saveData(AccountEntity(getBankName, getCardId, getMoney)).observe(this@AddAccountActivity, Observer {
                if (it != 0L) {
                    ToastUtils.showShort("保存成功")
                    finish()
                } else {
                    ToastUtils.showShort("保存失败")
                }
            })
        }
    }
}
