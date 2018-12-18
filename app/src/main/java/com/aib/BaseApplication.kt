package com.aib

import android.annotation.SuppressLint
import android.content.Context
import android.support.multidex.MultiDexApplication

/**
 * Application的基类
 *
 * 提供抽象方法
 */
abstract class BaseApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        initSdk()

        initData()

        initPath()
    }

    /**
     * 初始化SDK以及第三方库
     */
    abstract fun initSdk()

    abstract fun initData()

    abstract fun initPath()


    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            private set
    }
}

