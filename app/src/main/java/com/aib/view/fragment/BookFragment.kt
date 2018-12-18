package com.aib.view.fragment

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.View
import com.aib.di.Injectable
import com.aib.view.activity.AddAccountActivity
import com.aib.view.activity.MarkActivity
import com.aib.viewmodel.BookViewModel
import com.wm.loan.R
import com.wm.loan.databinding.FragmentBookBinding
import java.util.ArrayList

/**
 * 记账本
 */
class BookFragment : BaseFragment<FragmentBookBinding>(), Injectable {
    private var tabTitle = arrayOf("明细", "账户")  //标签标题
    private val CODE_0 = 0
    private val CODE_1 = 1
    private var fragments = ArrayList<Fragment>()
    private lateinit var vm: BookViewModel
    private var fragment1: BookTabFragment? = null
    private var fragment2: BookTabFragment? = null

    override fun getResId(): Int = R.layout.fragment_book

    override fun initData(savedInstanceState: Bundle?) {
        vm = getViewModel(BookViewModel::class.java)

        binding.presenter = Presenter()

        fragment1 = BookTabFragment()
        fragment2 = BookTabFragment()
        fragments.add(fragment1!!)
        fragments.add(fragment2!!)

        binding.vp.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            override fun getItem(p0: Int): Fragment = fragments.get(p0)

            override fun getCount(): Int = if (tabTitle.size == fragments.size) tabTitle.size else 0

            override fun getPageTitle(position: Int): CharSequence? {
                return tabTitle[position]
            }
        }

        binding.tl.setupWithViewPager(binding.vp)

        binding.tl.addOnTabSelectedListener(object : TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0!!.position) {
                    0 -> {
                        binding.btnEnter.text = "记一笔"
                        fragment1!!.passData(CODE_0)

                        vm.querySum("商品支出").observe(this@BookFragment, Observer {
                            var sum = 0
                            for (item in it!!) {
                                sum = sum + item.toInt()
                            }
                            binding.tvOut.text = sum.toString()
                        })
                    }
                    1 -> {
                        binding.btnEnter.text = "添加账户"
                        fragment2!!.passData(CODE_1)

                        vm.querySum("工资收入").observe(this@BookFragment, Observer {
                            var sum = 0
                            for (item in it!!) {
                                sum = sum + item.toInt()
                            }
                            binding.tvSum.text = sum.toString()
                        })
                    }
                }
            }
        })

        vm.querySum("商品支出").observe(this, Observer {
            var sum = 0
            for (item in it!!) {
                sum = sum + item.toInt()
            }
            binding.tvOut.text = sum.toString()
        })

        vm.querySum("工资收入").observe(this, Observer {
            var sum = 0
            for (item in it!!) {
                sum = sum + item.toInt()
            }
            binding.tvSum.text = sum.toString()
        })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CODE_0 -> {
                    fragment1!!.passData(CODE_0)
                }
                CODE_1 -> {
                    fragment2!!.passData(CODE_1)
                }
            }
        }
    }

    inner class Presenter {
        fun back(view: View) {
            activity!!.finish()
        }

        fun save(view: View) {
            val getName = binding.btnEnter.text.toString().trim()
            when (getName) {
                "记一笔" ->
                    startActivityForResult(Intent(activity!!, MarkActivity::class.java), CODE_0)
                "添加账户" ->
                    startActivityForResult(Intent(activity!!, AddAccountActivity::class.java), CODE_1)
            }
        }

        fun queryByDate(view: View) {
            val datePicker = DatePickerDialog(activity!!)
            datePicker.setOnDateSetListener { view, year, month, dayOfMonth ->
                fragment1!!.queryDetailByDate(year.toString() + "-" + (month + 1).toString() + "-" + dayOfMonth.toString())
            }
            datePicker.show()
        }
    }
}
