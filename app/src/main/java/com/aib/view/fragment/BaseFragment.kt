package com.aib.view.fragment

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aib.di.Injectable

import javax.inject.Inject

abstract class BaseFragment<D : ViewDataBinding> : Fragment(), Injectable {

    lateinit var binding: D
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var ctx: Context //上下文

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getResId(), container, false)
        return binding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ctx = activity!!.applicationContext
        initData(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (binding != null) {
            binding!!.unbind()
        }
    }

    fun <T : ViewModel> getViewModel(cls: Class<out T>): T {
        return ViewModelProviders.of(this, factory).get(cls)
    }

    abstract fun getResId(): Int

    abstract fun initData(savedInstanceState: Bundle?)
}
