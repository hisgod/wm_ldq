package com.aib.view.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.aib.adapter.BookTabAdapter
import com.aib.di.Injectable
import com.aib.viewmodel.BookTabViewModel
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.wm.loan.R
import com.wm.loan.databinding.FragmentBookTabBinding

/**
 * 记账本选项卡
 */
class BookTabFragment : BaseFragment<FragmentBookTabBinding>(), Injectable {
    private lateinit var vm: BookTabViewModel
    private var adapter: BookTabAdapter? = null

    override fun getResId(): Int = R.layout.fragment_book_tab

    override fun initData(savedInstanceState: Bundle?) {
        vm = getViewModel(BookTabViewModel::class.java)

        adapter = BookTabAdapter()
        binding.rv.adapter = adapter

        vm.queryDetails().observe(this, Observer {
            adapter!!.passFragment1Data(0, it)
            adapter!!.notifyDataSetChanged()
        })
    }

    fun passData(code: Int) {
        when (code) {
            0 -> {
                vm.queryDetails().observe(this, Observer {
                    adapter!!.passFragment1Data(0, it)
                    adapter!!.notifyDataSetChanged()
                })
            }
            1 -> {
                vm.queryAccount().observe(this, Observer {
                    adapter!!.passFragment2Data(1, it)
                    adapter!!.notifyDataSetChanged()
                })
            }
        }
    }

    fun queryDetailByDate(s: String) {
        vm.queryDetailByDate(s).observe(this, Observer {
            adapter!!.passFragment1Data(0, it)
            adapter!!.notifyDataSetChanged()
        })
    }
}