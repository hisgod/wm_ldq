package com.aib.entity


data class TypeLoanEntity(
    val applyCountFake: Int,
    val applyQualification: String,
    val des: String,
    val id: Int,
    val loanDay: Int,
    val loanMax: Double,
    val loanMin: Double,
    val loanRate: Double,
    val logo: String,
    val material: String,
    val name: String,
    val processIcon: String,
    val requestUrl: String,
    val tag: String
)