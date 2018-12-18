package com.aib.entity

import java.io.Serializable

class MsgCenterEntity : Serializable {

    /**
     * endRow : 4
     * firstPage : 1
     * hasNextPage : false
     * hasPreviousPage : false
     * isFirstPage : true
     * isLastPage : true
     * lastPage : 1
     * list : [{"acceptId":2,"acceptStatus":0,"acceptType":1,"id":4,"sendContent":"test4","sendTime":1532916960000,"sendTitle":"用户消息"},{"acceptId":2,"acceptStatus":0,"acceptType":1,"id":3,"sendContent":"test3","sendTime":1532916950000,"sendTitle":"用户消息"},{"acceptId":2,"acceptStatus":0,"acceptType":1,"id":2,"sendContent":"test2","sendTime":1532484940000,"sendTitle":"用户消息"},{"acceptId":2,"acceptStatus":0,"acceptType":1,"id":1,"sendContent":"gradient_1000","sendTime":1532312128000,"sendTitle":"用户消息"}]
     * navigatePages : 8
     * navigatepageNums : [1]
     * nextPage : 0
     * pageNum : 1
     * pageSize : 10
     * pages : 1
     * prePage : 0
     * size : 4
     * startRow : 1
     * total : 4
     */

    var endRow: Int = 0
    var firstPage: Int = 0
    var isHasNextPage: Boolean = false
    var isHasPreviousPage: Boolean = false
    var isIsFirstPage: Boolean = false
    var isIsLastPage: Boolean = false
    var lastPage: Int = 0
    var navigatePages: Int = 0
    var nextPage: Int = 0
    var pageNum: Int = 0
    var pageSize: Int = 0
    var pages: Int = 0
    var prePage: Int = 0
    var size: Int = 0
    var startRow: Int = 0
    var total: Int = 0
    var list: List<ListBean>? = null
    var navigatepageNums: List<Int>? = null

    class ListBean : Serializable {
        /**
         * acceptId : 2
         * acceptStatus : 0
         * acceptType : 1
         * id : 4
         * sendContent : test4
         * sendTime : 1532916960000
         * sendTitle : 用户消息
         */

        var acceptId: Int = 0
        var acceptStatus: Int = 0
        var acceptType: Int = 0
        var id: Int = 0
        var sendContent: String? = null
        var sendTime: Long = 0
        var sendTitle: String? = null
    }
}
