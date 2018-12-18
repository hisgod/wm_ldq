package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aib.entity.BaseEntity
import com.aib.entity.TypeLoanEntity
import com.aib.net.ApiService
import com.aib.net.Resource
import com.aib.net.RxObserver
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

class TypeListVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 根据关键字查询贷款列表
     */
    fun queryLoanListByKey(typeId: Int, i1: Int, i2: Int, isFirst: Boolean): LiveData<Resource<BaseEntity<ArrayList<TypeLoanEntity>>>> {
        val data = MutableLiveData<Resource<BaseEntity<ArrayList<TypeLoanEntity>>>>()
        apiService
                .QURY_LOAN_BY_TYPE(typeId, i1, i2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RxObserver<BaseEntity<ArrayList<TypeLoanEntity>>>() {
                    override fun onStart() {
                        if (isFirst) {
                            data.value = Resource.loading(null)
                        }
                    }

                    override fun onSuccess(t: BaseEntity<ArrayList<TypeLoanEntity>>) {
                        data.value = Resource.success(t)
                    }

                    override fun onFailure(t: Throwable) {
                        data.value = Resource.error(t.message, null)
                    }
                })
        return data
    }
}