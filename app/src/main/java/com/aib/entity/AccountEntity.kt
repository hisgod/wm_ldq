package com.aib.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

/**
 * 添加账户
 */

@Entity
data class AccountEntity(var accountName: String, @PrimaryKey(autoGenerate = false) var cardId: String, var money: String) : Serializable