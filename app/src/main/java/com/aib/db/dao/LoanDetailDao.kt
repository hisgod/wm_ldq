package com.aib.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.aib.entity.LoanDetailEntity

@Dao
interface LoanDetailDao {

    /**
     * 插入数据
     *
     * @param entity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: LoanDetailEntity)

    /**
     * 查询本地贷款平台数据列表
     *
     * @return
     */
    @Query("select * from LoanDetailEntity limit 10")
    fun quryAll(): LiveData<List<LoanDetailEntity>>

    /**
     * 根据ID查询对应的数据
     *
     * @param loanId
     * @return
     */
    @Query("select * from LoanDetailEntity where id=:loanId")
    fun qury(loanId: Int): LoanDetailEntity
}
