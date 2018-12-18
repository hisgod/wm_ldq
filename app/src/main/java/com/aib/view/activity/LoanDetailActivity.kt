package com.aib.view.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.SeekBar
import android.widget.TextView

import com.aib.entity.BaseEntity
import com.aib.utils.Constants
import com.aib.entity.LoanDetailEntity
import com.aib.net.Resource
import com.aib.net.Status
import com.aib.viewmodel.LoanDetailVm
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.ToastUtils

import java.util.ArrayList


import com.aib.utils.RetryCallback
import com.blankj.utilcode.util.BarUtils
import com.wm.loan.R
import com.wm.loan.databinding.ActivityLoanDetailBinding
import java.text.DecimalFormat

/**
 * 贷款平台详情
 */
class LoanDetailActivity : BaseActivity<ActivityLoanDetailBinding>() {

    private var vm: LoanDetailVm? = null
    private var id: Int = 0

    override fun getResId(): Int = R.layout.activity_loan_detail

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        binding.presenter = Presenter()

        id = intent.extras!!.getInt(Constants.LOAN_ID)

        vm = getViewModel(LoanDetailVm::class.java)

        getJson()

        binding.retry = object : RetryCallback {
            override fun retryClick() {
                getJson()
            }
        }
    }

    /**
     * 获取贷款详情
     */
    private fun getJson() {
        vm!!.getJson(id)!!.observe(this, object : Observer<Resource<BaseEntity<LoanDetailEntity>>> {
            override fun onChanged(baseEntityResource: Resource<BaseEntity<LoanDetailEntity>>?) {
                binding.resource = baseEntityResource
                if (baseEntityResource!!.status === Status.SUCCESS) {
                    if (baseEntityResource!!.data!!.code == 1) {
                        binding.entity = baseEntityResource.data!!.data

                        binding.tvTitle.text = baseEntityResource.data.data.name

                        val data = baseEntityResource.data.data

                        val builder = SpanUtils()
                                .append(baseEntityResource.data.data.applyCountFake.toString() + "").setForegroundColor(resources.getColor(R.color.color_ff9600))
                                .append("人已经申请")
                                .create()
                        binding.tvTip.text = builder

                        val strs = baseEntityResource.data.data.tag!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val textViews = ArrayList<TextView>()
                        textViews.add(binding.tv1)
                        textViews.add(binding.tv2)
                        textViews.add(binding.tv3)
                        run {
                            var i = 0
                            val len = strs.size
                            while (i < len) {
                                if (TextUtils.isEmpty(strs[i])) {
                                    textViews[i].visibility = View.GONE
                                } else {
                                    textViews[i].visibility = View.VISIBLE
                                    textViews[i].text = strs[i]
                                }
                                i++
                            }
                        }

                        for (i in textViews.indices) {
                            if (TextUtils.isEmpty(textViews[i].text.toString().trim { it <= ' ' })) {
                                textViews[i].visibility = View.GONE
                            } else {
                                textViews[i].visibility = View.VISIBLE
                            }
                        }


                        //立即申请
                        binding.btnApply.setOnClickListener {
                            enterWebView(baseEntityResource.data)
                        }

                        //日利率
                        binding.tvRate.text = DecimalFormat("0.00").format(baseEntityResource.data.data.loanRate * 100)

                        //预估利息
                        val getMoney = data.loanMin   //贷款金额
                        binding.tvSum.text = Math.round(data.loanRate * getMoney * 7).toString()

                        binding.seekbarMoney.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                                if (fromUser) {
                                    binding.etMoney.setText((progress + data.loanMin).toString())
                                    binding.tvSum.text = Math.round(data.loanRate * binding.etMoney.text.toString().trim().toDouble() * binding.etTime.text.toString().trim().toDouble()).toString()
                                }
                            }

                            override fun onStartTrackingTouch(seekBar: SeekBar) {

                            }

                            override fun onStopTrackingTouch(seekBar: SeekBar) {

                            }
                        })

                        binding.seekbarMonth.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                                if (fromUser) {
                                    binding.etTime.setText((progress + 7).toString())
                                    binding.tvSum.text = Math.round(data.loanRate * binding.etMoney.text.toString().trim().toDouble() * binding.etTime.text.toString().trim().toDouble()).toString()
                                }
                            }

                            override fun onStartTrackingTouch(seekBar: SeekBar) {

                            }

                            override fun onStopTrackingTouch(seekBar: SeekBar) {

                            }
                        })

                    } else {
                        ToastUtils.showShort(baseEntityResource.data!!.msg)
                        finish()
                    }
                }
            }
        })
    }

    /**
     * 进入WebView申请页面
     */
    fun enterWebView(data: BaseEntity<LoanDetailEntity>) {
        val args = Bundle()
        args.putString(Constants.URL, data.data.requestUrl)
        args.putInt(Constants.LOAN_ID, data.data.id)
        args.putString(Constants.LOAN_TITLE, data.data.name)
        ActivityUtils.startActivity(args, WebViewActivity::class.java)
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }

        fun showWeChat(view: View) {
            ActivityUtils.startActivity(CustomerActivity::class.java)
        }
    }
}
