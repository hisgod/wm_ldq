package com.aib.entity

import java.io.Serializable

class BottomNavigationEntity : Serializable {
    var android: String? = null
    var ios: String? = null
    var menu: List<MenuBean>? = null

    class MenuBean :Serializable{
        var className: String? = null
        var icon: String? = null
        var id: Int = 0
        var menuName: String? = null
        var orderNum: Int = 0
        var pageType: Int = 0
        var requestUrl: String? = null
        var status: Int = 0
    }
}
