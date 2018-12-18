package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aib.entity.BaseEntity
import com.aib.entity.CardDetailEntity
import com.aib.entity.CommentEntity
import com.aib.net.ApiService
import com.aib.net.Resource
import com.aib.net.RxObserver
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class CardDetailVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 帖子详情
     */
    fun getCardDetail(id: Int, isFirst: Boolean): MutableLiveData<Resource<BaseEntity<CardDetailEntity>>> {
        val data = MutableLiveData<Resource<BaseEntity<CardDetailEntity>>>()
        apiService
                .CARD_DETAIL(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RxObserver<BaseEntity<CardDetailEntity>>() {
                    override fun onStart() {
                        if (isFirst) {
                            data.value = Resource.loading(null)
                        }
                    }

                    override fun onSuccess(t: BaseEntity<CardDetailEntity>) {
                        if (t.code == 1) {
                            data.value = Resource.success(t)
                        } else {
                            data.value = Resource.error(t.msg, null)
                        }
                    }

                    override fun onFailure(t: Throwable) {
                        data.value = Resource.error(t.message, null)
                    }
                })
        return data
    }

    /**
     * 查看评论
     */
    fun lookCardComment(pageNum: Int, pageSize: Int, postId: Int): LiveData<BaseEntity<ArrayList<CommentEntity>>> {
        val data = MutableLiveData<BaseEntity<ArrayList<CommentEntity>>>()
        apiService
                .CARD_COMMENT_LIST(pageNum, pageSize, postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<ArrayList<CommentEntity>>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<ArrayList<CommentEntity>>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                    }
                })
        return data
    }

    /**
     * 发表评论
     */
    fun sendComment(content: String, postId: Int, token: String): LiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService
                .SEND_COMMENT(content, postId, token)
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