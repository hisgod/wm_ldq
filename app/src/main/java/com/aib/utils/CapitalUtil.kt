package com.aib.utils

/**
 * Description:等额本金工具类
 * Copyright: Copyright (Corporation)2015
 * Company: Corporation
 *
 * @version: 1.0
 * Modification History:
 * Modified by :
 */

import java.math.BigDecimal
import java.util.HashMap
import java.util.LinkedHashMap

/**
 * 等额本金是指一种贷款的还款方式，是在还款期内把贷款数总额等分，每月偿还同等数额的本金和剩余贷款在该月所产生的利息，这样由于每月的还款本金额固定，
 * 而利息越来越少，借款人起初还款压力较大，但是随时间的推移每月还款数也越来越少。
 */
object CapitalUtil {

    /**
     * 等额本金计算获取还款方式为等额本金的每月偿还本金和利息
     *
     *
     * 公式：每月偿还本金=(贷款本金÷还款月数)+(贷款本金-已归还本金累计额)×月利率
     *
     * @param invest   总借款额（贷款本金）
     * @param yearRate 年利率
     * @param month    还款总月数
     * @return 每月偿还本金和利息, 不四舍五入，直接截取小数点最后两位
     */
    fun getPerMonthPrincipalInterest(invest: Double, yearRate: Double, totalMonth: Int): LinkedHashMap<Int, Double> {
        val map = LinkedHashMap<Int, Double>()
        // 每月本金
        val monthPri = getPerMonthPrincipal(invest, totalMonth)
        // 获取月利率
        var monthRate = yearRate / 12
        monthRate = BigDecimal(monthRate).setScale(6, BigDecimal.ROUND_DOWN).toDouble()
        for (i in 1..totalMonth) {
            var monthRes = monthPri + (invest - monthPri * (i - 1)) * monthRate
            monthRes = BigDecimal(monthRes).setScale(2, BigDecimal.ROUND_DOWN).toDouble()
            map[i] = monthRes
        }
        return map
    }

    /**
     * 等额本金计算获取还款方式为等额本金的每月偿还利息
     *
     *
     * 公式：每月应还利息=剩余本金×月利率=(贷款本金-已归还本金累计额)×月利率
     *
     * @param invest   总借款额（贷款本金）
     * @param yearRate 年利率
     * @param month    还款总月数
     * @return 每月偿还利息
     */
    fun getPerMonthInterest(invest: Double, yearRate: Double, totalMonth: Int): Map<Int, Double> {
        val inMap = HashMap<Int, Double>()
        val principal = getPerMonthPrincipal(invest, totalMonth)
        val map = getPerMonthPrincipalInterest(invest, yearRate, totalMonth)
        for ((key, value) in map) {
            val principalBigDecimal = BigDecimal(principal)
            val principalInterestBigDecimal = BigDecimal(value)
            var interestBigDecimal = principalInterestBigDecimal.subtract(principalBigDecimal)
            interestBigDecimal = interestBigDecimal.setScale(2, BigDecimal.ROUND_DOWN)
            inMap[key] = interestBigDecimal.toDouble()
        }
        return inMap
    }

    /**
     * 等额本金计算获取还款方式为等额本金的每月偿还本金
     *
     *
     * 公式：每月应还本金=贷款本金÷还款月数
     *
     * @param invest   总借款额（贷款本金）
     * @param yearRate 年利率
     * @param month    还款总月数
     * @return 每月偿还本金
     */
    fun getPerMonthPrincipal(invest: Double, totalMonth: Int): Double {
        val monthIncome = BigDecimal(invest).divide(BigDecimal(totalMonth), 2, BigDecimal.ROUND_DOWN)
        return monthIncome.toDouble()
    }

    /**
     * 等额本金计算获取还款方式为等额本金的总利息
     *
     * @param invest   总借款额（贷款本金）
     * @param yearRate 年利率
     * @param month    还款总月数
     * @return 总利息
     */
    fun getInterestCount(invest: Double, yearRate: Double, totalMonth: Int): Double {
        var count = BigDecimal(0)
        val mapInterest = getPerMonthInterest(invest, yearRate, totalMonth)

        for ((_, value) in mapInterest) {
            count = count.add(BigDecimal(value))
        }
        return count.toDouble()
    }
}

