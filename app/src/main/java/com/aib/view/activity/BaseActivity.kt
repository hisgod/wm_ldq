package com.aib.view.activity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.BarUtils
import javax.inject.Inject
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector

/**
 * 所有Activity基类
 *
 * @param <D>
</D> */
abstract class BaseActivity<D : ViewDataBinding> : AppCompatActivity(), HasSupportFragmentInjector {
    lateinit var binding: D
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BarUtils.setStatusBarColor(this, Color.TRANSPARENT)

        binding = DataBindingUtil.setContentView(this, getResId())

        initData(savedInstanceState)
    }

    /**
     * 获得布局ID
     *
     * @return
     */
    abstract fun getResId(): Int

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    abstract fun initData(savedInstanceState: Bundle?)


    /**
     * 生成对应的ViewModel对象
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T : ViewModel> getViewModel(cls: Class<out T>): T {
        return ViewModelProviders.of(this, factory).get(cls)
    }

    /**
     * Dagger2全局配置
     *
     */
    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onDestroy() {
        super.onDestroy()
        //解除绑定
        if (binding != null) {
            binding!!.unbind()
        }
    }
}
