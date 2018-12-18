package com.aib.utils

/**
 * Description:等额本息工具类
 * Copyright: Copyright (corporation)2015
 * Company: Corporation
 *
 * @version: 1.0
 * Modification History:
 * Modified by :
 */

import java.math.BigDecimal
import java.util.HashMap

/**
 * 等额本息还款，也称定期付息，即借款人每月按相等的金额偿还贷款本息，其中每月贷款利息按月初剩余贷款本金计算并逐月结清。把按揭贷款的本金总额与利息总额相加，
 * 然后平均分摊到还款期限的每个月中。作为还款人，每个月还给银行固定金额，但每月还款额中的本金比重逐月递增、利息比重逐月递减。
 */

object InterestUtil {

    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还本金和利息
     *
     *
     * 公式：每月偿还本息=〔贷款本金×月利率×(1＋月利率)＾还款月数〕÷〔(1＋月利率)＾还款月数-1〕
     *
     * @param invest   总借款额（贷款本金）
     * @param yearRate 年利率
     * @param month    还款总月数
     * @return 每月偿还本金和利息, 不四舍五入，直接截取小数点最后两位
     */
    fun getPerMonthPrincipalInterest(invest: Double, yearRate: Double, totalmonth: Int): BigDecimal {
        val monthRate = yearRate / 12
        return BigDecimal(invest).multiply(BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth.toDouble()))).divide(BigDecimal(Math.pow(1 + monthRate, totalmonth.toDouble()) - 1), 2, BigDecimal.ROUND_UP)
    }

    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还利息
     *
     *
     * 公式：每月偿还利息=贷款本金×月利率×〔(1+月利率)^还款月数-(1+月利率)^(还款月序号-1)〕÷〔(1+月利率)^还款月数-1〕
     *
     * @param invest   总借款额（贷款本金）
     * @param yearRate 年利率
     * @param month    还款总月数
     * @return 每月偿还利息
     */
    fun getPerMonthInterest(invest: Double, yearRate: Double, totalmonth: Int): Map<Int, BigDecimal> {
        val map = HashMap<Int, BigDecimal>()
        val monthRate = yearRate / 12
        var monthInterest: BigDecimal
        for (i in 1 until totalmonth + 1) {
            val multiply = BigDecimal(invest).multiply(BigDecimal(monthRate))
            val sub = BigDecimal(Math.pow(1 + monthRate, totalmonth.toDouble()))
                    .subtract(BigDecimal(Math.pow(1 + monthRate, (i - 1).toDouble())))
            monthInterest = multiply.multiply(sub).divide(BigDecimal(Math.pow(1 + monthRate, totalmonth.toDouble()) - 1), 2,
                    BigDecimal.ROUND_DOWN)
            monthInterest = monthInterest.setScale(2, BigDecimal.ROUND_DOWN)
            map[i] = monthInterest
        }
        return map
    }

    /**
     * 等额本息计算获取还款方式为等额本息的每月偿还本金
     *
     * @param invest   总借款额（贷款本金）
     * @param yearRate 年利率
     * @param month    还款总月数
     * @return 每月偿还本金
     */
    fun getPerMonthPrincipal(invest: Double, yearRate: Double, totalmonth: Int): Map<Int, BigDecimal> {
        val monthRate = yearRate / 12
        val monthIncome = BigDecimal(invest)
                .multiply(BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth.toDouble())))
                .divide(BigDecimal(Math.pow(1 + monthRate, totalmonth.toDouble()) - 1), 2, BigDecimal.ROUND_DOWN)
        val mapInterest = getPerMonthInterest(invest, yearRate, totalmonth)
        val mapPrincipal = HashMap<Int, BigDecimal>()

        for ((key, value) in mapInterest) {
            mapPrincipal[key] = monthIncome.subtract(value)
        }
        return mapPrincipal
    }

    /**
     * 等额本息计算获取还款方式为等额本息的总利息
     *
     * @param invest   总借款额（贷款本金）
     * @param yearRate 年利率
     * @param month    还款总月数
     * @return 总利息
     */
    fun getInterestCount(invest: Double, yearRate: Double, totalmonth: Int): Double {
        var count = BigDecimal(0)
        val mapInterest = getPerMonthInterest(invest, yearRate, totalmonth)

        for ((_, value) in mapInterest) {
            count = count.add(value)
        }
        return count.toDouble()
    }

    /**
     * 应还本金总和
     *
     * @param invest   总借款额（贷款本金）
     * @param yearRate 年利率
     * @param month    还款总月数
     * @return 应还本息总和
     */
    fun getPrincipalInterestCount(invest: Double, yearRate: Double, totalmonth: Int): Double {
        val monthRate = yearRate / 12
        val perMonthInterest = BigDecimal(invest)
                .multiply(BigDecimal(monthRate * Math.pow(1 + monthRate, totalmonth.toDouble())))
                .divide(BigDecimal(Math.pow(1 + monthRate, totalmonth.toDouble()) - 1), 2, BigDecimal.ROUND_DOWN)
        var count = perMonthInterest.multiply(BigDecimal(totalmonth))
        count = count.setScale(2, BigDecimal.ROUND_DOWN)
        return count.toDouble()
    }


}

