package com.aib.view.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.UiThread
import android.support.annotation.WorkerThread
import android.util.Log

import com.aib.viewmodel.TestVm
import com.aib.viewmodel.ViewModelFactory
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.wm.loan.R
import com.wm.loan.databinding.ActivityTestBinding
import kotlinx.android.synthetic.main.activity_test.*
import javax.inject.Inject

class TestActivity : AppCompatActivity() {
    lateinit var binding: ActivityTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test)



    }
}
