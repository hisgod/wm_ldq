package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.util.ArrayMap

import com.aib.entity.BaseEntity
import com.aib.entity.CommitDataEntity
import com.aib.entity.NextStepEntity
import com.aib.net.ApiService
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class RelationVm @Inject constructor() : ViewModel() {

    @Inject
    lateinit var apiService: ApiService

    /**
     * 提交紧急联系人
     *
     * @param params
     */
    fun putEmergencyContact(str: String, token: String): LiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService
                .PUT_EMERGENCY_CONTACT(str, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe(object : Observer<BaseEntity<String>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<String>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.showShort(e.message)
                    }

                })
        return data
    }

    /**
     * 提交全部联系人
     */
    fun putAlllContacts(str: String, token: String): MutableLiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService
                .PUT_ALL_CONTACTS(str, token)
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
     * 下一个步骤
     *
     * @param token
     */
    fun nextStep(token: String): MutableLiveData<BaseEntity<NextStepEntity>> {
        val data = MutableLiveData<BaseEntity<NextStepEntity>>()
        apiService
                .NEXT_AUTH(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<NextStepEntity>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: BaseEntity<NextStepEntity>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.showShort(e.message)
                    }
                })
        return data
    }

    /**
     * 上传运营商认证
     */
    fun postOperator(token: String): MutableLiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService
                .POST_OPERATOR(token)
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
                        ToastUtils.showShort(e.message)
                    }
                })
        return data
    }
}
