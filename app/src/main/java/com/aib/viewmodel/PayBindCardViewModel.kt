package com.aib.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.util.ArrayMap
import com.aib.entity.BaseEntity
import com.aib.entity.PayBindCardEntity
import com.aib.net.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PayBindCardViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 支付宝行为绑卡
     */
    fun bindCardByAlipay(payMode: Int, token: String): MutableLiveData<BaseEntity<PayBindCardEntity>> {
        val data = MutableLiveData<BaseEntity<PayBindCardEntity>>()
        apiService
                .BIND_CARD_BY_ALIPAY(payMode, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<PayBindCardEntity>> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<PayBindCardEntity>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        return data
    }

    /**
     * 通过银行卡支付绑卡
     */
    fun bindCardByBank(payMode: Int, token: String): MutableLiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService
                .BIND_CARD_BY_BANK(payMode, token)
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

    /**
     * 获取绑卡费用
     */
    fun getBindCardConst(): MutableLiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService
                .BIND_CARD_COST()
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

    /**
     * 绑卡验证码
     */
    fun bindCardSms(params: ArrayMap<String, Any>): MutableLiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService
                .CONFIRM_BIND_CARD_SMS(params)
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