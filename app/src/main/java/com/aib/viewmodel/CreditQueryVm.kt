package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.aib.entity.BaseEntity
import com.aib.utils.Constants
import com.aib.entity.PayParamsEntity
import com.aib.entity.QueryCreditEntity
import com.aib.net.ApiService
import com.blankj.utilcode.util.SPUtils

import javax.inject.Inject

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CreditQueryVm @Inject
constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    fun getPayUrl(getCid: String, getName: String, getPhone: String): LiveData<BaseEntity<PayParamsEntity>> {
        val data = MutableLiveData<BaseEntity<PayParamsEntity>>()
        apiService!!
                .ALPLAY_URL(getCid, getName, 1, getPhone, SPUtils.getInstance().getString(Constants.TOKEN))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(object : Observer<BaseEntity<PayParamsEntity>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(alplayParamsEntityBaseEntity: BaseEntity<PayParamsEntity>) {
                        data.postValue(alplayParamsEntityBaseEntity)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        return data
    }

    /**
     * 查询报告
     *
     * @param token
     * @return
     */
    fun queryCredit(token: String?): LiveData<BaseEntity<QueryCreditEntity>> {
        val data = MutableLiveData<BaseEntity<QueryCreditEntity>>()
        apiService!!
                .QURY_CREDIT(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(object : Observer<BaseEntity<QueryCreditEntity>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(queryCreditEntityBaseEntity: BaseEntity<QueryCreditEntity>) {
                        data.postValue(queryCreditEntityBaseEntity)
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
        return data
    }
}
