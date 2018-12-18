package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.util.ArrayMap
import com.aib.entity.BandCardEntity
import com.aib.entity.BandCardListEntity
import com.aib.entity.BaseEntity
import com.aib.net.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

/**
 * 绑定银行卡ViewModel
 */
class BandCardViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 获取银行列表
     */
    fun getBandList(): LiveData<BaseEntity<ArrayList<BandCardListEntity>>> {
        val data = MutableLiveData<BaseEntity<ArrayList<BandCardListEntity>>>()
        apiService
                .BANK_LIST()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<ArrayList<BandCardListEntity>>> {
                    override fun onNext(t: BaseEntity<ArrayList<BandCardListEntity>>) {
                        data.value = t
                    }

                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        return data
    }

    /**
     * 绑定银行卡
     */
    fun bindBand(params: ArrayMap<String, Any>): MutableLiveData<BaseEntity<BandCardEntity>> {
        val data = MutableLiveData<BaseEntity<BandCardEntity>>()
        apiService
                .BIND_BAND(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<BandCardEntity>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<BandCardEntity>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        return data
    }

    /**
     * 通过短信绑定银行卡
     */
    fun bindCardMsm(params: ArrayMap<String, String>): MutableLiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService
                .CONFIRM_SMS(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<String>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<String>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        return data
    }
}