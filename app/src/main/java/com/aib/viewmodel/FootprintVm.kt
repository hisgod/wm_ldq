package com.aib.viewmodel

import android.arch.lifecycle.ViewModel
import com.aib.db.AppDataBase
import javax.inject.Inject

/**
 * 我的足迹VM
 */
class FootprintVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var db: AppDataBase

    /**
     * 查询本地数据
     */
    fun loadFromDb() = db.loanDetailDao.quryAll()
}
