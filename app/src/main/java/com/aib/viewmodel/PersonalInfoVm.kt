package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.aib.entity.BaseEntity
import com.aib.utils.Constants
import com.aib.net.ApiService
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class PersonalInfoVm @Inject constructor() : ViewModel() {

    @Inject
    lateinit var apiService: ApiService

    fun uploadSingleFile(filePath: String): LiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        val file = FileUtils.getFileByPath(filePath)
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val build = MultipartBody.Builder()
                .addFormDataPart("imgFile", file.name, requestBody)
                .addFormDataPart("token", SPUtils.getInstance().getString(Constants.TOKEN))
                .setType(MultipartBody.FORM)
                .build()
        apiService!!.UPLOAD_SINGLE_FILE(build)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe({ stringBaseEntity -> data.postValue(stringBaseEntity) }, { throwable -> ToastUtils.showLong(throwable.toString()) })
        return data
    }

    /**
     * 退出服务器登录
     *
     * @param token
     */
    fun userExit(token: String): LiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService!!.USER_EXIT(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe({ stringBaseEntity -> data.postValue(stringBaseEntity) }, { throwable -> ToastUtils.showShort(throwable.message) })
        return data
    }
}
