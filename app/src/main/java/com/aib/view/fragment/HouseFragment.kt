package com.aib.view.fragment

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View

import com.aib.adapter.HouseAdapter
import com.aib.utils.CapitalUtil
import com.aib.utils.InterestUtil
import com.aib.di.Injectable
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SnackbarUtils

import java.util.ArrayList

import com.wm.loan.R
import com.wm.loan.databinding.DialogLoanModeBinding
import com.wm.loan.databinding.FragmentHouseBinding
import com.wm.loan.databinding.ItemHouseHeadBinding

/**
 * 房贷计算器
 */
class HouseFragment : BaseFragment<FragmentHouseBinding>(), Injectable {

    private var headBinding: ItemHouseHeadBinding? = null
    private var adapter: HouseAdapter? = null
    private var checkedId: Int = 0

    override fun getResId(): Int = R.layout.fragment_house

    override fun initData(savedInstanceState: Bundle?) {
        binding.lv.setPadding(15, BarUtils.getStatusBarHeight(), 15, 0)
        headBinding = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.item_house_head, null, false)
        headBinding!!.presenter = Presenter()
        binding.lv.addHeaderView(headBinding!!.root)
        adapter = HouseAdapter()
        binding.lv.adapter = adapter


        headBinding!!.rg.setOnCheckedChangeListener { group, checkedId -> this@HouseFragment.checkedId = checkedId }
        headBinding!!.rg.check(R.id.rb_interest)
    }

    inner class Presenter {
        /**
         * 贷款方式
         *
         * @param view
         */
        fun showLoanMode(view: View) {
            val dialog = BottomSheetDialog(activity!!)
            val dialog_binding = DataBindingUtil.inflate<DialogLoanModeBinding>(LayoutInflater.from(ctx), R.layout.dialog_loan_mode, null, false)
            dialog.setContentView(dialog_binding.root)
            dialog.show()

            dialog_binding.tvSave.setOnClickListener {
                headBinding!!.tvMode.text = dialog_binding.tvSave.text.toString().trim { it <= ' ' }
                dialog.dismiss()
            }


            dialog_binding.tvBusiness.setOnClickListener {
                headBinding!!.tvMode.text = dialog_binding.tvBusiness.text.toString().trim { it <= ' ' }
                dialog.dismiss()
            }

            dialog_binding.tvCancel.setOnClickListener { dialog.dismiss() }
        }

        fun caculator(view: View) {
            val getMode = headBinding!!.tvMode.text.toString().trim { it <= ' ' }    //贷款方式
            val getCapital = headBinding!!.etCapital.text.toString().trim { it <= ' ' }      //金额
            val getRate = headBinding!!.etRate.text.toString().trim { it <= ' ' }    //利率
            val getYear = headBinding!!.etYear.text.toString().trim { it <= ' ' }    //年限

            if (getMode == "贷款方式") {
                SnackbarUtils.with(view).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(Color.WHITE).setMessage("请选择贷款方式").show()
                return
            }

            if (TextUtils.isEmpty(getCapital)) {
                SnackbarUtils.with(view).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(Color.WHITE).setMessage("请输入金额").show()
                return
            }

            if (TextUtils.isEmpty(getRate)) {
                SnackbarUtils.with(view).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(Color.WHITE).setMessage("请输入利率").show()
                return
            }

            if (TextUtils.isEmpty(getYear)) {
                SnackbarUtils.with(view).setBgColor(resources.getColor(R.color.colorAccent)).setMessageColor(Color.WHITE).setMessage("请输入年限").show()
                return
            }

            val capital = java.lang.Double.valueOf(getCapital) * 10000    //贷款本金
            val rate = java.lang.Double.valueOf(getRate) / 100    //年利率
            val month = Integer.valueOf(getYear) * 12  //贷款月数

            val interestMoney = InterestUtil.getPerMonthPrincipalInterest(capital, rate, month).toString() //每月还款
            val sumInterest = InterestUtil.getInterestCount(capital, rate, month)   //总利息
            val sumGive = InterestUtil.getPrincipalInterestCount(capital, rate, month)  //总共还款

            val map = CapitalUtil.getPerMonthPrincipalInterest(capital, rate, month)
            val interestCount1 = CapitalUtil.getInterestCount(capital, rate, month) //总利息
            val capitalSum = capital + interestCount1   //等额本金全部还款

            if (checkedId == headBinding!!.rbInterest.id) {
                val months = ArrayList<Int>()
                val moneys = ArrayList<String>()
                for (i in 0 until Integer.valueOf(getYear) * 12) {
                    months.add(i + 1)
                    moneys.add(interestMoney)
                }

                headBinding!!.tvMoneyMonth.text = interestMoney
                headBinding!!.tvSumInterest.text = sumInterest.toString()
                headBinding!!.tvSumGive.text = sumGive.toString()

                adapter!!.data(months, moneys)
                adapter!!.notifyDataSetChanged()
            } else {
                val months = ArrayList<Int>()
                val moneys = ArrayList<String>()
                for ((key, value) in map) {
                    months.add(key)
                    moneys.add(value.toString())
                }

                headBinding!!.tvMoneyMonth.text = map[1].toString()
                headBinding!!.tvSumInterest.text = interestCount1.toString()
                headBinding!!.tvSumGive.text = capitalSum.toString()

                adapter!!.data(months, moneys)
                adapter!!.notifyDataSetChanged()
            }
        }
    }
}
