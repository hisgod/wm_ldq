package com.aib.view.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View

import com.aib.adapter.FootprintAdapter
import com.aib.viewmodel.FootprintVm


import com.aib.net.Resource
import com.blankj.utilcode.util.BarUtils
import com.wm.loan.R
import com.wm.loan.databinding.ActivityFootprintBinding

/**
 * 我的足迹
 */
class FootprintActivity : BaseActivity<ActivityFootprintBinding>() {

    override fun getResId(): Int=R.layout.activity_footprint

    override fun initData(savedInstanceState: Bundle?) {
        binding.tb.setPadding(0,BarUtils.getStatusBarHeight(),0,0)

        val vm = getViewModel(FootprintVm::class.java)

        binding.presenter = Presenter()

        val adapter = FootprintAdapter()
        binding.rv.adapter = adapter

        vm.loadFromDb().observe(this, Observer {
            if (it!!.isEmpty()) {
                binding.resource = Resource.empty(null)
            } else {
                binding.resource = Resource.success(it)
                adapter.setData(it)
                adapter.notifyDataSetChanged()
            }
        })
    }

    inner class Presenter {
        /**
         * 返回
         *
         * @param view
         */
        fun back(view: View) {
            finish()
        }
    }
}
