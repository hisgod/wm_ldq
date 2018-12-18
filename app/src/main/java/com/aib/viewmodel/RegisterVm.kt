package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.aib.entity.BaseEntity
import com.aib.net.ApiService
import com.blankj.utilcode.util.ToastUtils

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class RegisterVm @Inject
constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 获取验证码
     *
     * @param phone
     * @return
     */
    fun getVertification(phone: String): LiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService!!.VERTIFICATION_CODE(phone, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe({ stringBaseEntity -> data.postValue(stringBaseEntity) }, { throwable -> ToastUtils.showShort(throwable.message) })
        return data
    }

    /**
     * 注册
     *
     * @param phone
     * @param pwd
     * @param code
     * @param smsToken 34：极光
     * 45:Vivo
     * @return
     */
    fun register(phone: String, pwd: String, code: String, smsToken: String?, channelId: Int): LiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService!!.USER_REGISTER(phone, pwd, code, smsToken, channelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe({ stringBaseEntity -> data.postValue(stringBaseEntity) }, { throwable -> ToastUtils.showShort(throwable.message) })
        return data
    }
}
