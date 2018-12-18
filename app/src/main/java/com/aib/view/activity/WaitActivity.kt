package com.aib.view.activity

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View

import com.aib.adapter.VertifyBottomAdapter
import com.aib.adapter.VertifyCenterAdapter
import com.aib.adapter.VertifyTopAdapter
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.blankj.utilcode.util.BarUtils
import com.wm.loan.R
import com.wm.loan.databinding.ActivityWaitBinding

/**
 * 等待审核
 */
class WaitActivity : BaseActivity<ActivityWaitBinding>() {

    override fun getResId(): Int = R.layout.activity_wait

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0,BarUtils.getStatusBarHeight(),0,0)

        binding.presenter = Presenter()

        val pool = RecyclerView.RecycledViewPool()
        pool.setMaxRecycledViews(0, 20)
        val manager = VirtualLayoutManager(this)
        binding.rv.setRecycledViewPool(pool)
        binding.rv.layoutManager = manager
        val adapter = DelegateAdapter(manager)
        val vertifyTopAdapter = VertifyTopAdapter()
        val vertifyCenterAdapter = VertifyCenterAdapter()
        val vertifyBottomAdapter = VertifyBottomAdapter()
        adapter.addAdapter(vertifyTopAdapter)
        adapter.addAdapter(vertifyCenterAdapter)
        adapter.addAdapter(vertifyBottomAdapter)
        binding.rv.adapter = adapter
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }
    }
}
