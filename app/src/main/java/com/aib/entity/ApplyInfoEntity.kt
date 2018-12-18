package com.aib.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

class ApplyInfoEntity {
    var applyCountFake: Int = 0
    var des: String? = null
    var icon: String? = null
    var id: Int = 0
    var isJump: Int = 0
    var loanRange: String? = null
    var orderNum: Int = 0
    var prodId: Int = 0
    var requestUrl: String? = null
    var title: String? = null
}
