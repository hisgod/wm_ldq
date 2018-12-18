package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aib.db.AppDataBase

import com.aib.entity.BaseEntity
import com.aib.entity.LoanDetailEntity
import com.aib.net.ApiService
import com.aib.net.Resource
import com.aib.net.RxObserver

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class LoanDetailVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService
    @Inject
    lateinit var db: AppDataBase

    fun getJson(loanId: Int): LiveData<Resource<BaseEntity<LoanDetailEntity>>>? {
        val data = MutableLiveData<Resource<BaseEntity<LoanDetailEntity>>>()
        apiService
                .LOAN_DETAIL(loanId)
                .doOnNext {
                    db.loanDetailDao.save(it.data)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RxObserver<BaseEntity<LoanDetailEntity>>() {
                    override fun onStart() {
                        data.value = Resource.loading(null)
                    }

                    override fun onSuccess(t: BaseEntity<LoanDetailEntity>) {
                        data.value = Resource.success(t)
                    }

                    override fun onFailure(t: Throwable) {
                        data.value = Resource.error(t.message, null)
                    }
                })
        return data
    }
}
