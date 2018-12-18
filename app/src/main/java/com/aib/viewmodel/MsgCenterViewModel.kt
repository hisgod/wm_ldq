package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel

import com.aib.entity.BaseEntity
import com.aib.entity.MsgCenterEntity
import com.aib.net.ApiService
import com.aib.net.Resource

import javax.inject.Inject


class MsgCenterViewModel @Inject
constructor() : ViewModel() {

    @Inject
    lateinit var apiService: ApiService

    fun getMsgList(token: String, page: Int, size: Int): LiveData<Resource<BaseEntity<MsgCenterEntity>>>? {
        //        return new NetworkResource<BaseEntity<MsgCenterEntity>,MsgCenterEntity>() {
        //            @Override
        //            public Observable<BaseEntity<MsgCenterEntity>> createCall() {
        //                return apiService.MSG_CENTER_LIST(token, page, size);
        //            }
        //
        //            @Override
        //            public void save(BaseEntity<MsgCenterEntity> data) {
        //
        //            }
        //
        //            @Override
        //            public MsgCenterEntity loadFromDb() {
        //                return null;
        //            }
        //        }.asLiveData();
        return null
    }
}
