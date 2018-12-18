package com.aib.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class LoanDetailEntity {

    /**
     * applyCountFake : 75213
     * applyQualification : 18周岁以上，实名电话半年以上芝麻分500以上
     * des : 门槛低，最快10分钟即可到账10000
     * id : 77
     * loanDay : 180
     * loanMax : 20000
     * loanMin : 1000
     * loanRate : 3.0E-4
     * logo : http://www.smoneybag.com:8090/proImg/04b8955e-c4b1-4a59-9c83-43ff56b2c19e.png
     * material : 身份证，银行卡
     * name : 麦粒贷
     * processIcon : http://www.smoneybag.com:8090/proImg/3b67a06b-6697-4b1e-af46-cea333ce0947.png
     * requestUrl : http://wwt.hkxlxhl.top/jyp.page#/reg?channelid=721
     * tag : 芝麻分贷,小额短期,通过率高
     */

    var applyCountFake: Int = 0
    var applyQualification: String? = null
    var des: String? = null
    @PrimaryKey
    var id: Int = 0
    var loanDay: Int = 0
    var loanMax: Int = 0
    var loanMin: Int = 0
    var loanRate: Double = 0.toDouble()
    var logo: String? = null
    var material: String? = null
    var name: String? = null
    var processIcon: String? = null
    var requestUrl: String? = null
    var tag: String? = null
}
