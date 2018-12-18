package com.aib.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.aib.entity.MarkEntity

@Dao
interface MarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(entity: MarkEntity): Long

    @Query("select * from MarkEntity")
    fun query(): LiveData<List<MarkEntity>>

    @Query("select MarkEntity.money from MarkEntity where type=:type")
    fun querySum(type: String): LiveData<List<String>>

    /**
     * 根据时间查询对应的数据
     */
    @Query("select * from MarkEntity where date=:date")
    fun queryDetailByDate(date: String): LiveData<List<MarkEntity>>
}