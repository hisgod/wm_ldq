package com.aib.entity



data class CardListEntity(
    val commentnum: Int,
    val content: String,
    val createTime: String,
    val id: Int,
    val likenum: Int,
    val tag: String,
    val tagColor: String,
    val thumbnail: String,
    val title: String
)