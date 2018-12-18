package com.aib.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class MarkEntity(
        @PrimaryKey(autoGenerate = true) var id: Int=1,
        val date: String,
        val money: String,
        val type: String,
        val account: String,
        val bz: String
)