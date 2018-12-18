package com.aib.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class AgreementEntity {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
    var onlyLabel: String? = null
    var title: String? = null
    var content: String? = null
    var imgUrl: Any? = null
    var createUserId: Int = 0
    var createTime: Long = 0
    var createUpdateId: Any? = null
    var updateTime: Any? = null
}
