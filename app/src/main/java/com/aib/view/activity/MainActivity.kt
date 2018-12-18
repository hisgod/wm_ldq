package com.aib.view.activity

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.aib.entity.BaseEntity
import com.aib.entity.BottomNavigationEntity

import com.aib.utils.Constants
import com.aib.viewmodel.MainViewModel

import java.util.ArrayList


import com.aib.view.fragment.*
import com.blankj.utilcode.util.*
import com.wm.loan.R
import com.wm.loan.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private var mExitTime: Long = 0
    lateinit var viewModel: MainViewModel
    var fragmentList: MutableList<Fragment> = ArrayList()
    private val tabTitles = ArrayList<String>()
    private val tabIcon = ArrayList<Int>()
    private val textColors = ArrayList<Int>()
    private val rb_home = 0
    var rb_all = 1
    private val rb_certain = 2
    private val rb_credit = 3
    private val rb_center = 4
    private val rb_card = 5

    override fun getResId(): Int =R.layout.activity_main

    @SuppressLint("ResourceType")
    override fun initData(savedInstanceState: Bundle?) {
        viewModel = getViewModel(MainViewModel::class.java)

        //获取清单文件meta值
        val applicationInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA)
        val channel = applicationInfo.metaData.getString("BaiduMobAd_CHANNEL")
//        LogUtils.e(channel)
        viewModel.getBottomJson(channel!!, AppUtils.getAppVersionName()).observe(this, Observer {
            parseBottomJson(it)
        })
    }

    /**
     * 解析底部数据
     */
    private fun parseBottomJson(it: BaseEntity<BottomNavigationEntity>?) {
        binding.line.visibility = View.VISIBLE  //显示分割线
        if (it!!.code == 1) {
            if (it.data.android == AppUtils.getAppVersionName()) {
                fragmentList.add(HouseFragment())
                fragmentList.add(BookFragment())
                fragmentList.add(CardFragment())

                tabTitles.add("房贷计算")
                tabTitles.add("记账本")
                tabTitles.add("交流区")
            } else {
                for (i in 0 until it.data.menu!!.size) {

                    val entity = it.data.menu!![i]

                    if (entity.status == 1) {
                        //status  :1显示，0不显示
                        when (entity.orderNum) {
                            1 -> {
                                val recFragment = HomeFragment()
                                val args = Bundle()
                                args.putString(Constants.TITLE_TEXT, entity.menuName)
                                recFragment.arguments = args
                                fragmentList.add(recFragment)
                                tabTitles.add(entity.menuName!!)
                                when (entity.icon) {
                                    "recom" -> tabIcon.add(R.drawable.selector_bottom_rec_bg)
                                    "loans" -> tabIcon.add(R.drawable.selector_bottom_all_bg)
                                    "allLoans" -> tabIcon.add(R.drawable.selector_bottom_certain_bg)
                                    "credit" -> tabIcon.add(R.drawable.selector_bottom_credit_bg)
                                    "mine" -> tabIcon.add(R.drawable.selector_bottom_center_bg)
                                    "community" -> tabIcon.add(R.drawable.selector_bottom_card_bg)
                                }
                                textColors.add(R.drawable.selector_bottom_rec_textcolor)
                            }
                            2 -> {
                                val allFragment = MoreFragment()
                                val allFragmentArgs = Bundle()
                                allFragmentArgs.putString(Constants.TITLE_TEXT, entity.menuName)
                                allFragment.arguments = allFragmentArgs
                                fragmentList.add(allFragment)
                                tabTitles.add(entity.menuName!!)
                                when (entity.icon) {
                                    "recom" -> tabIcon.add(R.drawable.selector_bottom_rec_bg)
                                    "loans" -> tabIcon.add(R.drawable.selector_bottom_all_bg)
                                    "allLoans" -> tabIcon.add(R.drawable.selector_bottom_certain_bg)
                                    "credit" -> tabIcon.add(R.drawable.selector_bottom_credit_bg)
                                    "mine" -> tabIcon.add(R.drawable.selector_bottom_center_bg)
                                    "community" -> tabIcon.add(R.drawable.selector_bottom_card_bg)
                                }
                                textColors.add(R.drawable.selector_bottom_rec_textcolor)
                            }
                            3 -> {
//                                val certainFragment = CertainFragment()
//                                fragmentList.add(certainFragment)
//                                val certainFragmentArgs = Bundle()
//                                certainFragmentArgs.putString(Constants.TITLE_TEXT, entity.menuName)
//                                certainFragment.arguments = certainFragmentArgs
//                                tabTitles.add(entity.menuName!!)
//                                tabIcon.add(R.drawable.selector_bottom_certain_bg)
//                                textColors.add(R.drawable.selector_bottom_rec_textcolor)

                                //暂时显示旧版
                                val certainFragment = OldCertainFragment()
                                fragmentList.add(certainFragment)
                                val certainFragmentArgs = Bundle()
                                certainFragmentArgs.putString(Constants.TITLE_TEXT, entity.menuName)
                                certainFragment.arguments = certainFragmentArgs
                                tabTitles.add(entity.menuName!!)
                                when (entity.icon) {
                                    "recom" -> tabIcon.add(R.drawable.selector_bottom_rec_bg)
                                    "loans" -> tabIcon.add(R.drawable.selector_bottom_all_bg)
                                    "allLoans" -> tabIcon.add(R.drawable.selector_bottom_certain_bg)
                                    "credit" -> tabIcon.add(R.drawable.selector_bottom_credit_bg)
                                    "mine" -> tabIcon.add(R.drawable.selector_bottom_center_bg)
                                    "community" -> tabIcon.add(R.drawable.selector_bottom_card_bg)
                                }
                                textColors.add(R.drawable.selector_bottom_rec_textcolor)
                            }
                            4 -> {
                                val creditFragment = CreditFragment()
                                val creditFragmentArgs = Bundle()
                                creditFragmentArgs.putString(Constants.TITLE_TEXT, entity.menuName)
                                creditFragment.arguments = creditFragmentArgs
                                fragmentList.add(creditFragment)
                                tabTitles.add(entity.menuName!!)
                                when (entity.icon) {
                                    "recom" -> tabIcon.add(R.drawable.selector_bottom_rec_bg)
                                    "loans" -> tabIcon.add(R.drawable.selector_bottom_all_bg)
                                    "allLoans" -> tabIcon.add(R.drawable.selector_bottom_certain_bg)
                                    "credit" -> tabIcon.add(R.drawable.selector_bottom_credit_bg)
                                    "mine" -> tabIcon.add(R.drawable.selector_bottom_center_bg)
                                    "community" -> tabIcon.add(R.drawable.selector_bottom_card_bg)
                                }
                                textColors.add(R.drawable.selector_bottom_rec_textcolor)
                            }
                            5 -> {
                                val centerFragment = CenterFragment()
                                val centerFragmentArgs = Bundle()
                                centerFragmentArgs.putString(Constants.TITLE_TEXT, entity.menuName)
                                centerFragment.arguments = centerFragmentArgs
                                fragmentList.add(centerFragment)
                                tabTitles.add(entity.menuName!!)
                                when (entity.icon) {
                                    "recom" -> tabIcon.add(R.drawable.selector_bottom_rec_bg)
                                    "loans" -> tabIcon.add(R.drawable.selector_bottom_all_bg)
                                    "allLoans" -> tabIcon.add(R.drawable.selector_bottom_certain_bg)
                                    "credit" -> tabIcon.add(R.drawable.selector_bottom_credit_bg)
                                    "mine" -> tabIcon.add(R.drawable.selector_bottom_center_bg)
                                    "community" -> tabIcon.add(R.drawable.selector_bottom_card_bg)
                                }
                                textColors.add(R.drawable.selector_bottom_rec_textcolor)
                            }
                            6 -> {
                                val cardFragment = CardFragment()
                                val argss = Bundle()
                                argss.putString(Constants.TITLE_TEXT, entity.menuName)
                                cardFragment.arguments = argss
                                fragmentList.add(cardFragment)
                                tabTitles.add(entity.menuName!!)
                                when (entity.icon) {
                                    "recom" -> tabIcon.add(R.drawable.selector_bottom_rec_bg)
                                    "loans" -> tabIcon.add(R.drawable.selector_bottom_all_bg)
                                    "allLoans" -> tabIcon.add(R.drawable.selector_bottom_certain_bg)
                                    "credit" -> tabIcon.add(R.drawable.selector_bottom_credit_bg)
                                    "mine" -> tabIcon.add(R.drawable.selector_bottom_center_bg)
                                    "community" -> tabIcon.add(R.drawable.selector_bottom_card_bg)
                                }
                                textColors.add(R.drawable.selector_bottom_rec_textcolor)
                            }
                        }
                    }
                }
            }

            for (i in fragmentList.indices) {
                val rb = RadioButton(this@MainActivity)
                rb.width = 0
                rb.text = tabTitles[i]
                rb.textSize = 12f
                rb.setTextColor(resources.getColorStateList(R.drawable.selector_bottom_rec_textcolor))
                rb.buttonDrawable = null
                rb.gravity = Gravity.CENTER
                rb.setBackgroundResource(Color.TRANSPARENT)
                if (!tabIcon.isEmpty()) {
                    val drawable = resources.getDrawable(tabIcon[i])
                    drawable.setBounds(0, 0, ConvertUtils.dp2px(23f), ConvertUtils.dp2px(23f))
                    rb.setCompoundDrawables(null, drawable, null, null)
                }

                when (i) {
                    0 -> rb.id = rb_home
                    1 -> rb.id = rb_all
                    2 -> rb.id = rb_certain
                    3 -> rb.id = rb_credit
                    4 -> rb.id = rb_center
                    5 -> rb.id = rb_card
                }

                binding.rg.addView(rb)

                val params = rb.layoutParams as RadioGroup.LayoutParams
                params.weight = 1F
                params.gravity = Gravity.CENTER
                rb.layoutParams = params
            }

            binding.rg.check(rb_home)
            switchFragment(0)

            binding.rg.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    0 -> switchFragment(0)
                    1 -> switchFragment(1)
                    2 -> switchFragment(2)
                    3 -> switchFragment(3)
                    4 -> switchFragment(4)
                    5 -> switchFragment(5)
                }
            }
        } else {
            ToastUtils.showShort(it.msg)
        }
    }

    /**
     * 哪一个Fragment
     */
    fun switchFragment(position: Int) {
        val ft = supportFragmentManager.beginTransaction()
        for (i in fragmentList.indices) {
            val fragment = fragmentList[i]
            if (i == position) {
                if (fragment.isAdded) {
                    ft.show(fragment)
                } else {
                    ft.add(R.id.fl, fragment)
                }
            } else {
                if (fragment.isAdded) {
                    ft.hide(fragment)
                }
            }
        }
        ft.commitNowAllowingStateLoss()
    }

    override fun onStart() {
        super.onStart()
        //保存是否第一次进到首页
        ThreadUtils.getIoPool().submit { SPUtils.getInstance().put(Constants.IS_FIRST, true) }
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            mExitTime = System.currentTimeMillis()
            ToastUtils.showShort("再按一次退出")
        } else {
            super.onBackPressed()
        }
    }
}