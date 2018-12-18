package com.aib.db

import android.arch.persistence.room.TypeConverter


object Converters {
    @TypeConverter
    fun string2Obj(value: String?): Any? {
        return value
    }

    @TypeConverter
    fun obj2String(obj: Any?): String? {
        return obj?.toString()
    }

}
