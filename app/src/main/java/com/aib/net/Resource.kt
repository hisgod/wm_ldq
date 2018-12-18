package com.aib.net

/**
 * 网络请求状态
 */
data class Resource<out T>(val status: Status, val data: T?, val msg: String?) {
    companion object {
        /**
         * 请求成功
         */
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        /**
         * 请求出错
         */
        fun <T> error(msg: String?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        /**
         * 请求中
         */
        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, "加载中...")
        }

        /**
         * 空数据
         */
        fun <T> empty(data: T?): Resource<T> {
            return Resource(Status.EMPTY, data, null)
        }
    }
}
