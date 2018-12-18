package com.aib.view.fragment

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aib.di.Injectable

import javax.inject.Inject

/**
 * Fragment懒加载
 */
abstract class LazyFragment<D : ViewDataBinding> : Fragment(), Injectable {
    //Fragment的View加载完毕的标记
    private var isViewCreated: Boolean = false

    //Fragment对用户可见的标记
    private var isUIVisible: Boolean = false
    lateinit var binding: D

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    abstract val resId: Int

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true
            lazyLoad()
        } else {
            isUIVisible = false
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, resId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        lazyLoad()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    private fun lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            loadData()
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false
            isUIVisible = false
        }
    }

    fun <T : ViewModel> getViewModel(cls: Class<T>): T {
        return ViewModelProviders.of(this, factory).get<T>(cls)
    }

    abstract fun loadData()
}
