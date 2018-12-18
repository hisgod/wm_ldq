package com.aib.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aib.entity.BaseEntity
import com.aib.entity.HomeEntity
import com.aib.net.ApiService
import com.aib.net.Resource
import com.aib.net.RxObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * 首页ViewModel
 *
 */
class HomeViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 获取首页数据
     */
    fun getHomeJson(isFirst: Boolean): MutableLiveData<Resource<HomeEntity>> {
        val data = MutableLiveData<Resource<HomeEntity>>()
        apiService
                .HOME()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RxObserver<BaseEntity<HomeEntity>>() {
                    override fun onStart() {
                        if (isFirst) {
                            data.value = Resource.loading(null)
                        }
                    }

                    override fun onSuccess(t: BaseEntity<HomeEntity>) {
                        data.value = Resource.success(t.data)
                    }

                    override fun onFailure(t: Throwable) {
                        data.value = Resource.error(t.message, null)
                    }
                })
        return data
    }
}
