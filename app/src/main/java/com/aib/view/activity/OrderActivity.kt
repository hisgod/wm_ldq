package com.aib.view.activity

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View

import com.aib.adapter.OrderAdapter
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.wm.loan.R
import com.wm.loan.databinding.ActivityOrderBinding

/**
 * 我的订单
 */
class OrderActivity : BaseActivity<ActivityOrderBinding>() {

    override fun getResId(): Int=R.layout.activity_order

    override fun initData(savedInstanceState: Bundle?) {
        binding.presenter = Presenter()

        val pool = RecyclerView.RecycledViewPool()
        pool.setMaxRecycledViews(0, 10)
        binding.rv.setRecycledViewPool(pool)
        val manager = VirtualLayoutManager(applicationContext)
        binding.rv.layoutManager = manager
        val adapter = DelegateAdapter(manager)
        adapter.addAdapter(OrderAdapter())
        binding.rv.adapter = adapter
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }
    }
}
