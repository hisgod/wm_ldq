package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.aib.entity.BaseEntity
import com.aib.db.AppDataBase
import com.aib.entity.AgreementEntity
import com.aib.net.ApiService
import com.aib.net.Resource
import com.aib.net.RxObserver

import javax.inject.Inject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 用户协议ViewModel
 */
class AgreementVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    fun getAgreementJson(text: String, isFirst: Boolean): LiveData<Resource<AgreementEntity>> {
        val data = MutableLiveData<Resource<AgreementEntity>>()
        apiService.AGREEMENT(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RxObserver<BaseEntity<AgreementEntity>>() {
                    override fun onSuccess(t: BaseEntity<AgreementEntity>) {
                        data.value = Resource.success(t.data)
                    }

                    override fun onStart() {
                        if (isFirst) {
                            data.value = Resource.loading(null)
                        }
                    }

                    override fun onFailure(t: Throwable) {
                        data.value = Resource.error(t.message, null)
                    }
                })
        return data
    }
}
