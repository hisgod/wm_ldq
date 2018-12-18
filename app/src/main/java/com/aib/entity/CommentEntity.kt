package com.aib.entity

/**
 * 帖子评论
 */
data class CommentEntity(
        val content: String,
        val createTime: String,
        val nickname: String,
        val headimg: String
)