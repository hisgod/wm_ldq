package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.aib.db.AppDataBase
import com.aib.entity.MarkEntity
import com.blankj.utilcode.util.ToastUtils
import javax.inject.Inject

class BookTabViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var db: AppDataBase

    /**
     * 查询明细
     */
    fun queryDetails() = db.markDao.query()

    /**
     * 查询账户
     */
    fun queryAccount() = db.accountDao.query()

    /**
     * 根据时间查询对应的数据
     */
    fun queryDetailByDate(date: String): LiveData<List<MarkEntity>> {
        return db.markDao.queryDetailByDate(date)
    }
}