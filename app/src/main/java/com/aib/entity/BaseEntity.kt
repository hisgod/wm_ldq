package com.aib.entity

import java.io.Serializable

/**
 * 统一Entity数据类的基类
 */
data class BaseEntity<T>(var code: Int, var msg: String, var data: T) : Serializable