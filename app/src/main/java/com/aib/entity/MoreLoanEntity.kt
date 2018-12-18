package com.aib.entity

import java.io.Serializable
import java.text.DecimalFormat

class MoreLoanEntity : Serializable {

    /**
     * id : 10
     * name : gogo贷
     * des : 最新一款秒贷产品p
     * logo : 商户logo图片路径
     * loanMin : 500
     * loanMax : 3000
     * loanDay : 15
     * loanRate : 0.24
     * tag : 新口子,芝麻分500,速下款
     * processIcon : 申请流程图路径
     * applyQualification : 年满18岁，芝麻分600以上
     * material : 身份证正反面,银行卡
     * requestUrl : 跳转路径
     * applyCountFake : 3002
     */

    var id: Int = 0
    var name: String? = null
    var des: String? = null
    var logo: String? = null
    var loanMin: Int = 0
    var loanMax: Int = 0
    var loanDay: Int = 0
    var loanRate: Double = 0.toDouble()
    var tag: String? = null
    var processIcon: String? = null
    var applyQualification: String? = null
    var material: String? = null
    var requestUrl: String? = null
    var applyCountFake: Int = 0
}
