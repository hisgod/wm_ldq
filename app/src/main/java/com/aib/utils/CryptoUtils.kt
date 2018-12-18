package com.aib.utils


import android.support.v4.util.ArrayMap

import com.blankj.utilcode.util.EncryptUtils
import java.util.SortedMap
import java.util.TreeMap

/**
 * Created by Seven on 16/2/23.
 */
object CryptoUtils {
    fun codePswd(testMap: ArrayMap<String, String>, tokenSecret: String): String {
        val paramMap = createParamMap(testMap)
        return makeSha1Hash(paramMap, tokenSecret)
    }

    /**
     * 请求参数的 排序【hashmap】
     *
     * @param request
     * @return
     */
    fun createParamMap(request: ArrayMap<String, String>): SortedMap<String, String> {
        val sort = TreeMap(request)
        val entry1 = sort.entries
        val it = entry1.iterator()
        while (it.hasNext()) {
            val entry = it.next()
        }
        return sort
    }

    /**
     * 请求参数的加密
     *
     * @param paramMap 排序后的请求参数
     * @param secret   秘钥
     * @return
     */
    fun makeSha1Hash(paramMap: SortedMap<String, String>?, secret: String): String {
        val sb = StringBuilder(32)

        if (paramMap != null) {
            for ((key, value) in paramMap) {
                sb.append(key).append(value)
            }
        }
        val source1 = sb.toString()
        return EncryptUtils.encryptHmacSHA1ToString(source1, secret)
    }
}
