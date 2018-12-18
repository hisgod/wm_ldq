package com.aib.viewmodel

import android.arch.lifecycle.ViewModel

import com.aib.entity.BaseEntity
import com.aib.net.ApiService

import javax.inject.Inject

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class WebVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 增加贷款产品点击量
     */
    fun addLoanClick(loanId: Int, token: String) {
        apiService
                .LOAN_CLICK_COUNT(loanId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<String>> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(stringBaseEntity: BaseEntity<String>) {

                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
    }
}
