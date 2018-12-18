package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aib.db.AppDataBase
import com.aib.entity.AccountEntity
import com.aib.entity.MarkEntity
import com.blankj.utilcode.util.ThreadUtils
import javax.inject.Inject

class MarkViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var db: AppDataBase

    /**
     * 查询本地数据是否有账户
     */
    fun queryAccount() = db.accountDao.query()

    /**
     * 保存记一记
     */
    fun save(entity: MarkEntity): LiveData<Long> {
        val data = MutableLiveData<Long>()
        ThreadUtils.getIoPool().submit {
            data.postValue(db.markDao.save(entity))
        }
        return data
    }
}