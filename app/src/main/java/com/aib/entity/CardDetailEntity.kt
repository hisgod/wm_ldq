package com.aib.entity

import java.io.Serializable

/**
 * 帖子详情
 */
data class CardDetailEntity(
        val commentnum: Int,
        val content: String,
        val id: Int,
        val likenum: Int,
        val nickname: String,
        val originalImageArr: ArrayList<String>,
        val publishTime: String,
        val title: String
) : Serializable