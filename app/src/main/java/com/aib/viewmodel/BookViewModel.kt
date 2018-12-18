package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.aib.db.AppDataBase
import javax.inject.Inject

class BookViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var db: AppDataBase

    /**
     * 查询明细
     */
    fun queryDetails() = db.markDao.query()

    /**
     * 查询支出/收入
     */
    fun querySum(type: String): LiveData<List<String>> {
        return db.markDao.querySum(type)
    }
}