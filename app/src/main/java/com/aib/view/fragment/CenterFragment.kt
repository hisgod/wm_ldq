package com.aib.view.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.TextUtils
import android.view.View

import com.aib.utils.Constants
import com.aib.view.activity.CustomerActivity
import com.aib.view.activity.FootprintActivity
import com.aib.view.activity.LoginActivity
import com.aib.view.activity.MsgCenterActivity
import com.aib.view.activity.OrderActivity
import com.aib.view.activity.PersonalInfoActivity
import com.aib.view.activity.WebViewActivity
import com.aib.viewmodel.CenterFragmentVm
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

import java.util.ArrayList

import com.wm.loan.R
import com.wm.loan.databinding.FragmentCenterBinding

/**
 * 我的
 */
class CenterFragment : BaseFragment<FragmentCenterBinding>() {

    private var vm: CenterFragmentVm? = null

    override fun getResId(): Int =R.layout.fragment_center

    override fun initData(savedInstanceState: Bundle?) {
        //状态栏
        BarUtils.setStatusBarAlpha(activity!!, 0)
        binding.tb.setPadding(0, BarUtils.getStatusBarHeight() + 15, 0, 25)

        vm = getViewModel(CenterFragmentVm::class.java)

        //设置标题
        val getTitle = arguments!!.getString(Constants.TITLE_TEXT)
        if (TextUtils.isEmpty(getTitle)) {
            binding.tvTitle.text = "标题"
        } else {
            binding.tvTitle.text = getTitle
        }

        //控制器
        binding.presenter = Presenter()

    }

    /**
     * 显示微信对话框
     */
    private fun showWechatDialog() {
        ActivityUtils.startActivity(CustomerActivity::class.java)
    }

    override fun onStart() {
        super.onStart()

        ThreadUtils.executeByIo(object : ThreadUtils.Task<List<String>>() {
            @Throws(Throwable::class)
            override fun doInBackground(): List<String>? {
                val token = SPUtils.getInstance().getString(Constants.TOKEN)
                val headImgPath = SPUtils.getInstance().getString(Constants.HEAD_IMG)
                val account = SPUtils.getInstance().getString(Constants.PHONE)

                val data = ArrayList<String>()
                data.add(token)
                data.add(headImgPath)
                data.add(account)

                return data
            }

            override fun onSuccess(result: List<String>?) {
                if (result!![0] === "") {
                    Glide.with(ctx)
                            .load(R.drawable.ic_default_img)
                            .apply(RequestOptions().circleCrop().placeholder(R.drawable.ic_img_loading).error(R.drawable.ic_img_error))
                            .into(binding.iv)
                    binding.tvPhone.text = "请登录"
                } else {
                    Glide.with(ctx)
                            .load(result!![1])
                            .apply(RequestOptions().circleCrop().placeholder(R.drawable.ic_img_loading).error(R.drawable.ic_img_error))
                            .into(binding.iv)
                    binding.tvPhone.text = result[2]
                }
            }

            override fun onCancel() {

            }

            override fun onFail(t: Throwable) {

            }
        })
    }

    inner class Presenter {
        fun openWechat(view: View) {
            showWechatDialog()
        }

        /**
         * 进入消息中心
         *
         * @param view
         */
        fun enterMsgCnter(view: View) {
            ActivityUtils.startActivity(MsgCenterActivity::class.java)
        }

        /**
         * 我的足迹
         *
         * @param view
         */
        fun enterFootPrint(view: View) {
            ActivityUtils.startActivity(FootprintActivity::class.java)
        }

        /**
         * 进入编辑页面
         *
         * @param view
         */
        fun enterCenter(view: View) {
            if (SPUtils.getInstance().getString(Constants.TOKEN) === "") {
                ActivityUtils.startActivity(LoginActivity::class.java)
            } else {
                ActivityUtils.startActivity(PersonalInfoActivity::class.java)
            }
        }

        fun enterHistory(view: View) {
            ThreadUtils.getIoPool().submit(object : ThreadUtils.Task<String>() {
                @Throws(Throwable::class)
                override fun doInBackground(): String? {
                    return SPUtils.getInstance().getString(Constants.TOKEN)
                }

                override fun onSuccess(result: String?) {
                    if (TextUtils.isEmpty(result)) {
                        ActivityUtils.startActivity(LoginActivity::class.java)
                    } else {
                        vm!!.queryCredit(result).observe(this@CenterFragment, Observer { queryCreditEntityBaseEntity ->
                            if (queryCreditEntityBaseEntity!!.code == 1) {
                                val args = Bundle()
                                args.putString(Constants.URL, queryCreditEntityBaseEntity.data.creditRptLink)
                                ActivityUtils.startActivity(args, WebViewActivity::class.java)
                            } else {
                                ToastUtils.showShort("获取数据失败")
                            }
                        })
                    }
                }

                override fun onCancel() {

                }

                override fun onFail(t: Throwable) {
                    ToastUtils.showShort(t.message)
                }
            })
        }

        fun enterOrder(view: View) {
            ActivityUtils.startActivity(OrderActivity::class.java)
        }
    }
}
