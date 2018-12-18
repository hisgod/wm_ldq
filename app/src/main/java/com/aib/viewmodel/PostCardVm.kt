package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.util.ArrayMap
import com.aib.entity.BaseEntity
import com.aib.entity.CardTagEntity
import com.aib.net.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.ArrayList
import javax.inject.Inject

class PostCardVm @Inject constructor() : ViewModel() {
    @Inject
    internal lateinit var api: ApiService

    /**
     * 发布帖子
     */
    fun postCard(body: ArrayMap<String, RequestBody>): LiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        api
                .POST_CARD(body)
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
     * 获取发帖标签
     */
    fun tagList(): LiveData<BaseEntity<List<CardTagEntity>>> {
        val data = MutableLiveData<BaseEntity<List<CardTagEntity>>>()
        api
                .TAG_LIST()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<List<CardTagEntity>>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<List<CardTagEntity>>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        return data
    }
}