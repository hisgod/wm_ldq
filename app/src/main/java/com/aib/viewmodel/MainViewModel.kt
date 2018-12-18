package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aib.entity.BaseEntity
import com.aib.entity.BottomNavigationEntity

import com.aib.net.ApiService
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.nio.channels.spi.AbstractInterruptibleChannel

import javax.inject.Inject

/**
 * MainActivity的ViewModel
 */
class MainViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 获取底部导航栏数据
     */
    fun getBottomJson(channel: String, version: String): LiveData<BaseEntity<BottomNavigationEntity>> {
        val data = MutableLiveData<BaseEntity<BottomNavigationEntity>>()
        apiService
                .BOTTOM_NAVIGATION_LIST(channel,version)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<BottomNavigationEntity>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<BottomNavigationEntity>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.showShort(e.message)
                    }
                })
        return data
    }
}
