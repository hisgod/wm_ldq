package com.aib.view.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.aib.adapter.AccountListAdapter
import com.aib.entity.AccountEntity
import com.aib.viewmodel.AccountListViewModel
import com.blankj.utilcode.util.BarUtils
import com.wm.loan.R
import com.wm.loan.databinding.ActivityAccountListBinding

/**
 * 华为渠道包
 *
 * 账户列表
 */
class AccountListActivity : BaseActivity<ActivityAccountListBinding>() {

    private lateinit var vm: AccountListViewModel

    override fun getResId(): Int = R.layout.activity_account_list

    override fun initData(savedInstanceState: Bundle?) {
        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        binding.presenter = Presenter()

        vm = getViewModel(AccountListViewModel::class.java)

        val adapter = AccountListAdapter()
        binding.rv.adapter = adapter
        adapter.setOnItemClickListener(object : AccountListAdapter.OnItemClickListener {
            override fun onItemClick(position: Int, item: AccountEntity) {
                setResult(Activity.RESULT_OK, intent.putExtra("itemData", item))
                finish()
            }
        })

        vm.queryAccounts().observe(this, Observer {
            adapter.passData(it)
            adapter.notifyDataSetChanged()
        })
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }
    }
}
