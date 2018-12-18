package com.aib.view.activity

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v4.util.ArrayMap
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.aib.adapter.BandListAdapter
import com.aib.entity.BandCardListEntity
import com.aib.utils.CommonFun
import com.aib.utils.Constants

import com.aib.viewmodel.BandCardViewModel
import com.blankj.utilcode.util.*
import com.wm.loan.R
import com.wm.loan.databinding.ActivityBindCardBinding


/**
 * 绑定银行卡
 */
class BindCardActivity : BaseActivity<ActivityBindCardBinding>() {
    lateinit var vm: BandCardViewModel
    override fun getResId(): Int = R.layout.activity_bind_card
    private var bandCode: String? = null    //银行CODE
    private var cardType = 0    //卡种

    override fun initData(savedInstanceState: Bundle?) {
        vm = getViewModel(BandCardViewModel::class.java)
        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

//        binding.rg.setOnCheckedChangeListener { group, checkedId ->
//            when (checkedId) {
//                R.id.rb1 -> cardType = 0
//                R.id.rb2 -> cardType = 1
//            }
//        }

        binding.presenter = Presenter()
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }

        /**
         * 下一步
         */
        fun next(view: View) {
            val getCardId = binding.etCardId.text.toString().trim()
            val getBandName = binding.tvBandName.text.toString().trim() //银行卡名字
            val getPhone = binding.etPhone.text.toString().trim()

            if (TextUtils.isEmpty(getCardId)) {
                ToastUtils.showShort("请输入银行卡号")
                return
            }

            if (!CommonFun.checkBankCard(getCardId)) {
                ToastUtils.showShort("银行卡号错误")
                return
            }

            if (getBandName.equals("请选择选择所属银行")) {
                ToastUtils.showShort("请选择所属银行卡")
                return
            }

            if (TextUtils.isEmpty(getPhone)) {
                ToastUtils.showShort("请输入手机号")
                return
            }

            if (!RegexUtils.isMobileExact(getPhone)) {
                ToastUtils.showShort("请输入正确手机号")
                return
            }


            val params = ArrayMap<String, Any>()
            params.put("bankCode", bandCode)
            params.put("bankName", getBandName)
            params.put("cardByName", SPUtils.getInstance().getString(Constants.USER_NAME))  //名字
            params.put("cardByNo", getCardId)
            params.put("cardType", cardType)
            params.put("cerNumber", SPUtils.getInstance().getString(Constants.USER_ID_CARD))    //身份证
            params.put("mobile", getPhone)
            params.put("token", SPUtils.getInstance().getString(Constants.TOKEN))
            vm.bindBand(params).observe(this@BindCardActivity, Observer {
                if (it!!.code == 1) {
                    val startPhone = getPhone.substring(0, 3)
                    val endPhone = getPhone.substring(getPhone.length - 3, getPhone.length)
                    val builder = AlertDialog.Builder(this@BindCardActivity)
                    builder.setCancelable(false)
                    val dialog = builder.setView(R.layout.dialog_input_validate).create()
                    dialog.show()
                    val tv_phone = dialog.findViewById<TextView>(R.id.tv_phone)
                    tv_phone!!.text = startPhone + "***" + endPhone
                    val et = dialog.findViewById<EditText>(R.id.et)
                    val btnYes = dialog.findViewById<Button>(R.id.btn_yes)
                    btnYes.setOnClickListener { v ->
                        val getCode = et.text.toString().trim()
                        if (TextUtils.isEmpty(getCode)) {
                            ToastUtils.showShort("验证码不能为空")
                            return@setOnClickListener
                        }
                        var params = ArrayMap<String, String>()
                        params.put("requestno", it.data.requestno)
                        params.put("token", SPUtils.getInstance().getString(Constants.TOKEN))
                        params.put("validatecode", getCode)
                        vm.bindCardMsm(params).observe(this@BindCardActivity, Observer {
                            if (it!!.code == 1) {
                                ActivityUtils.startActivity(PayBindCardActivity::class.java)
                            } else {
                                ToastUtils.showShort(it.msg)
                            }
                        })
                    }
                    val btnNo = dialog.findViewById<Button>(R.id.btn_no)
                    btnNo.setOnClickListener {
                        dialog.dismiss()
                    }

                } else {
                    ToastUtils.showShort(it.msg)
                    finish()
                }
            })
        }

        /**
         * 显示银行卡列表
         */
        fun showBand(view: View) {
            vm.getBandList().observe(this@BindCardActivity, Observer {
                if (it!!.code == 1) {
                    val dialog = BottomSheetDialog(this@BindCardActivity)
                    dialog.setContentView(R.layout.dialog_band_list)
                    val rv = dialog.findViewById<RecyclerView>(R.id.rv)
                    val adapter = BandListAdapter(it)
                    rv!!.adapter = adapter

                    adapter.setOnItemClickListener(object : BandListAdapter.OnItemClickListener {
                        override fun onItemClick(itemEntity: BandCardListEntity) {
                            binding.tvBandName.text = itemEntity.cnName
                            bandCode = itemEntity.code
                            dialog.dismiss()
                        }
                    })
                    dialog.show()
                } else {
                    ToastUtils.showShort(it.msg)
                }
            })
        }
    }
}
