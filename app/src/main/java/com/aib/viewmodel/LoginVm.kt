package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.aib.entity.BaseEntity
import com.aib.entity.UserEntity
import com.aib.net.ApiService
import com.blankj.utilcode.util.ToastUtils

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class LoginVm @Inject
constructor() : ViewModel() {

    @Inject
    lateinit var apiService: ApiService

    /**
     * 登陆
     *
     * @param phone
     * @param pwd
     */
    fun login(phone: String, pwd: String): LiveData<BaseEntity<UserEntity>> {
        val data = MutableLiveData<BaseEntity<UserEntity>>()
        apiService!!.USER_LOGIN(phone, pwd).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe({ loginActivityBaseEntity -> data.postValue(loginActivityBaseEntity) }, { throwable -> ToastUtils.showShort(throwable.message) })
        return data
    }
}
