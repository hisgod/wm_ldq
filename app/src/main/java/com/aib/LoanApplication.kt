package com.aib

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

import com.aib.di.DaggerAppComponent
import com.aib.di.Injectable
import com.aib.service.InitializeService
import com.blankj.utilcode.util.FileUtils
import com.squareup.leakcanary.LeakCanary

import javax.inject.Inject

import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector

/**
 * LoanApplication
 */
class LoanApplication : BaseApplication(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun initSdk() {
        val intent = Intent(this, InitializeService::class.java)
        startService(intent)

        //LeakCanary
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
    }

    override fun initData() {
        daggerInject()
    }

    override fun initPath() {
        //创建文件夹
        FileUtils.createOrExistsDir(getCacheDir().getAbsolutePath() + "/img")
    }


    /**
     * Dagger2全局注入
     */
    private fun daggerInject() {
        DaggerAppComponent.builder().application(this).build().inject(this)
        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                //Activity注入
                if (activity is HasSupportFragmentInjector) {
                    AndroidInjection.inject(activity)
                }

                //Fragment注入
                if (activity is FragmentActivity) {
                    val appCompatActivity = activity as AppCompatActivity
                    appCompatActivity.supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                            super.onFragmentAttached(fm, f, context)
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true)
                }

            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }
}
