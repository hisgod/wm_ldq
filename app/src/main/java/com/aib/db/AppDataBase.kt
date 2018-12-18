package com.aib.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.aib.db.dao.AccountDao

import com.aib.db.dao.LoanDetailDao
import com.aib.db.dao.MarkDao
import com.aib.entity.AccountEntity
import com.aib.entity.AgreementEntity
import com.aib.entity.LoanDetailEntity
import com.aib.entity.MarkEntity

/**
 * 数据库对象
 */
@Database(entities = [
    LoanDetailEntity::class,
    AccountEntity::class,
    MarkEntity::class
], version = 6, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    //贷款平台详情
    abstract val loanDetailDao: LoanDetailDao

    //银行卡账户
    abstract val accountDao: AccountDao

    //记一记
    abstract val markDao: MarkDao
}
