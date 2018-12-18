package com.aib.entity

/**
 * 下一个步骤
 */
data class NextStepEntity(
        val nextStep_zh_CN: String,
        val nextStep: String,
        val finish: Boolean,
        val info: Any
)