package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aib.db.AppDataBase
import com.aib.entity.AccountEntity
import com.blankj.utilcode.util.ThreadUtils
import javax.inject.Inject

class AccountViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var db: AppDataBase

    /**
     * 保存数据
     */
    fun saveData(entity: AccountEntity): LiveData<Long> {
        val data = MutableLiveData<Long>()
        ThreadUtils.getIoPool().submit {
            data.postValue(db.accountDao.save(entity))
        }
        return data
    }
}