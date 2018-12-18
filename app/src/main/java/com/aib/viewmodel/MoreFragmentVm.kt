package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.util.ArrayMap

import com.aib.entity.BaseEntity
import com.aib.entity.MoreLoanEntity
import com.aib.entity.TypeEntity
import com.aib.net.ApiService
import com.aib.net.Resource
import com.aib.net.RxObserver

import java.util.ArrayList

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoreFragmentVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 获取分类列表
     */
    val typeList: LiveData<BaseEntity<ArrayList<TypeEntity>>>
        get() {
            val data = MutableLiveData<BaseEntity<ArrayList<TypeEntity>>>()
            apiService.TYPE_LIST()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread(), true)
                    .subscribe({ arrayListBaseEntity -> data.postValue(arrayListBaseEntity) }, { })
            return data
        }


    /**
     * 获取综合排序
     */
    fun getDefaultJson(page: Int, size: Int, isFirst: Boolean): LiveData<Resource<BaseEntity<ArrayList<MoreLoanEntity>>>> {
        val data = MutableLiveData<Resource<BaseEntity<ArrayList<MoreLoanEntity>>>>()
        apiService
                .ZH_SORT(page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RxObserver<BaseEntity<ArrayList<MoreLoanEntity>>>() {
                    override fun onStart() {
                        if (isFirst) {
                            data.value = Resource.loading(null)
                        }
                    }

                    override fun onSuccess(t: BaseEntity<ArrayList<MoreLoanEntity>>) {
                        data.value = Resource.success(t)
                    }

                    override fun onFailure(t: Throwable) {
                        data.value = Resource.error(t.message, null)
                    }
                })
        return data
    }


    /**
     * 查询产品
     */
    fun queryProduct(params: ArrayMap<String, Any>): LiveData<BaseEntity<ArrayList<MoreLoanEntity>>> {
        val data = MutableLiveData<BaseEntity<ArrayList<MoreLoanEntity>>>()
        apiService.QUERY(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribe({ arrayListBaseEntity -> data.postValue(arrayListBaseEntity) }, { })
        return data
    }
}
