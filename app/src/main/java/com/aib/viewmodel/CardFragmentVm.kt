package com.aib.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.util.ArrayMap
import com.aib.entity.BaseEntity
import com.aib.entity.CardListEntity
import com.aib.net.ApiService
import com.aib.net.Resource
import com.aib.net.RxObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList
import javax.inject.Inject

class CardFragmentVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 帖子列表
     */
    fun getCardList(params: ArrayMap<String, Any>, isFirst: Boolean): MutableLiveData<Resource<BaseEntity<ArrayList<CardListEntity>>>> {
        val data = MutableLiveData<Resource<BaseEntity<ArrayList<CardListEntity>>>>()
        apiService.CARD_LIST(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RxObserver<BaseEntity<ArrayList<CardListEntity>>>() {
                    override fun onStart() {
                        if (isFirst) {
                            data.value = Resource.loading(null)
                        }
                    }

                    override fun onSuccess(t: BaseEntity<ArrayList<CardListEntity>>) {
                        if (t.data == null) {
                            data.value = Resource.empty(null)
                        } else {
                            if (t.code == 1) {
                                data.value = Resource.success(t)

                            } else {
                                data.value = Resource.error(t.msg, null)
                            }
                        }
                    }

                    override fun onFailure(t: Throwable) {
                        data.value = Resource.error(t.message, null)
                    }
                })
        return data
    }
}