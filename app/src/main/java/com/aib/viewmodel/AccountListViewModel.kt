package com.aib.viewmodel

import android.arch.lifecycle.ViewModel
import com.aib.db.AppDataBase
import javax.inject.Inject

class AccountListViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var db: AppDataBase

    /**
     * 查询账户列表
     */
    fun queryAccounts() = db.accountDao.query()
}