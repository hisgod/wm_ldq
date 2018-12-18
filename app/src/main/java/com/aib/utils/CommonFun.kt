package com.aib.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

import com.blankj.utilcode.util.ToastUtils

import java.security.MessageDigest

/**
 * static调用方法
 */
object CommonFun {
    fun isWeixinAvilible(context: Context): Boolean {
        val packageManager = context.packageManager// 获取packagemanager
        val pinfo = packageManager.getInstalledPackages(0)// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (i in pinfo.indices) {
                val pn = pinfo[i].packageName
                if (pn == "com.tencent.mm") {
                    return true
                }
            }
        }
        return false
    }


    /**
     * 复制文本到剪切板
     */
    fun cpyText(context: Context, text: String, showMsg: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.primaryClip = ClipData.newPlainText(null, text)
        ToastUtils.showShort(showMsg)
    }

    /**
     * 加密为小写字母
     *
     * @param str
     * @return
     */
    fun md5(str: String): String {
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(str.toByteArray())
            val b = md.digest()
            val sb = StringBuffer()
            for (i in b.indices) {
                var v = b[i].toInt()
                v = if (v < 0) 0x100 + v else v
                val cc = Integer.toHexString(v)
                if (cc.length == 1)
                    sb.append('0')
                sb.append(cc)
            }
            return sb.toString()
        } catch (e: Exception) {
            ToastUtils.showShort(e.message)
        }

        return ""
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    fun checkBankCard(cardId: String): Boolean {
        val bit = getBankCardCheckCode(cardId.substring(0, cardId.length - 1))
        return if (bit == 'N') {
            false
        } else cardId[cardId.length - 1] == bit
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    fun getBankCardCheckCode(nonCheckCodeCardId: String?): Char {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim { it <= ' ' }.length == 0
                || !nonCheckCodeCardId.matches("\\d+".toRegex())) {
            // 如果传的不是数据返回N
            return 'N'
        }
        val chs = nonCheckCodeCardId.trim { it <= ' ' }.toCharArray()
        var luhmSum = 0
        var i = chs.size - 1
        var j = 0
        while (i >= 0) {
            var k = chs[i] - '0'
            if (j % 2 == 0) {
                k *= 2
                k = k / 10 + k % 10
            }
            luhmSum += k
            i--
            j++
        }
        return if (luhmSum % 10 == 0) '0' else (10 - luhmSum % 10 + '0'.toInt()).toChar()
    }

}
