package com.aib.entity

/**
 * 用户实体
 *
 *
 * 登录，免登录可用
 */
class UserEntity {


    /**
     * userId : 2
     * token : APPUSER:6610089300001
     * phone : 15360060187
     * headImg : http://192.168.2.124:8080/loanMarket/appUser/img/201807/appUser/455036d5-7df5-44ce-8fb9-5b4df4fcd875.jpeg
     * lastLoginTime : 1532748653853
     * status : 1
     * tokenSecret : null
     */

    var userId: Int = 0
    var token: String? = null
    var phone: String? = null
    var headImg: String? = null
    var lastLoginTime: Long = 0
    var status: Int = 0
    var tokenSecret: String? = null
}
