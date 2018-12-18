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

class ForgetPwdVm @Inject
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
        apiService!!.VERTIFICATION_CODE(phone, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe({ stringBaseEntity -> data.postValue(stringBaseEntity) }, { throwable -> ToastUtils.showShort(throwable.message) })
        return data
    }

    /**
     * 修改密码
     *
     * @param phone
     * @param pwd
     * @param code
     * @param smsToken
     * @return
     */
    fun modifyPwd(phone: String, pwd: String, code: String, smsToken: String?): LiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService!!.FORGET_PWD(phone, pwd, code, smsToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe({ stringBaseEntity -> data.postValue(stringBaseEntity) }, { throwable -> ToastUtils.showShort(throwable.message) })
        return data
    }
}
