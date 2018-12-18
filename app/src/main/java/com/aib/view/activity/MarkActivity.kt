package com.aib.view.activity

import android.app.Activity
import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.aib.entity.AccountEntity
import com.aib.entity.MarkEntity
import com.aib.viewmodel.MarkViewModel
import com.blankj.utilcode.util.*
import com.wm.loan.R
import com.wm.loan.databinding.ActivityMarkBinding
import java.text.SimpleDateFormat

/**
 * 记账
 */
class MarkActivity : BaseActivity<ActivityMarkBinding>() {
    private lateinit var vm: MarkViewModel

    private val CODE_0 = 0

    override fun getResId(): Int = R.layout.activity_mark

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0,BarUtils.getStatusBarHeight(),0,0)

        binding.tvDate.text = TimeUtils.getNowString(SimpleDateFormat("yyyy-MM-d"))

        binding.presenter = Presenter()

        vm = getViewModel(MarkViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CODE_0 -> {
                    val data: AccountEntity = data!!.getSerializableExtra("itemData") as AccountEntity
                    binding.tvAccount.text = data.cardId
                }
            }
        }
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }

        /**
         * 选择时间
         */
        fun selectTime() {
            val datePicker = DatePickerDialog(this@MarkActivity)
            datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
                binding.tvDate.text = year.toString() + "-" + (month + 1).toString() + "-" + dayOfMonth.toString()
            }
            datePicker.show()
        }

        fun selectType(view: View) {
            val dialog = BottomSheetDialog(this@MarkActivity)
            dialog.setContentView(R.layout.dialog_use_type)
            val tv_in = dialog.findViewById<TextView>(R.id.tv_in)
            val tv_out = dialog.findViewById<TextView>(R.id.tv_out)
            tv_in!!.setOnClickListener {
                binding.tvUseType.text = tv_in.text.toString().trim()
                dialog.dismiss()
            }
            tv_out!!.setOnClickListener {
                binding.tvUseType.text = tv_out.text.toString().trim()
                dialog.dismiss()
            }
            dialog.show()
        }

        fun selectAccount(view: View) {
            vm.queryAccount().observe(this@MarkActivity, Observer {
                if (it!!.size == 0) {
                    ActivityUtils.startActivity(AddAccountActivity::class.java)
                } else {
                    ActivityUtils.startActivityForResult(this@MarkActivity, AccountListActivity::class.java, CODE_0)
                }
            })
        }

        /**
         * 保存
         */
        fun save() {
            val getDate = binding.tvDate.text.toString().trim()
            val getMoney = binding.etMoney.text.toString().trim()
            val getType = binding.tvUseType.text.toString().trim()
            val getAccount = binding.tvAccount.text.toString().trim()
            val getBz = binding.etBz.text.toString().trim()

            if (TextUtils.isEmpty(getDate)) {
                ToastUtils.showShort("请选择日期")
                return
            }
            if (TextUtils.isEmpty(getMoney)) {
                ToastUtils.showShort("请输入金额")
                return
            }
            if (getType.equals("请选择")) {
                ToastUtils.showShort("请选择类型")
                return
            }
            if (getAccount.equals("请选择")) {
                ToastUtils.showShort("请选择账户")
                return
            }

            var entity: MarkEntity? = null
            when (getType) {
                "工资收入" -> {
                    entity = MarkEntity(0, getDate, "+" + getMoney, getType, getAccount, getBz)
                }
                "商品支出" -> {
                    entity = MarkEntity(0, getDate, "-" + getMoney, getType, getAccount, getBz)
                }
            }

            vm.save(entity!!).observe(this@MarkActivity, Observer {
                if (it == 0L) {
                    ToastUtils.showShort("保存失败")
                } else {
                    ToastUtils.showShort("保存成功")
                    setResult(Activity.RESULT_OK)
                    finish()
                }
            })
        }
    }
}
