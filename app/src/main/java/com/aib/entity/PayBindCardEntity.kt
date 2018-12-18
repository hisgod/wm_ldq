package com.aib.entity

/**
 * 绑定银行卡支付费用Entity
 */
data class PayBindCardEntity(
    val h5Url: String,
    val merchantOutOrderNo: String,
    val payMode: Int,
    val url: String
)