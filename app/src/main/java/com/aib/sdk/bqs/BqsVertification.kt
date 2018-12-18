package com.aib.sdk.bqs

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import com.wm.loan.R
import com.aib.utils.Constants
import com.bqs.crawler.cloud.sdk.BqsCrawlerCloudSDK
import com.bqs.crawler.cloud.sdk.BqsParams
import com.bqs.crawler.cloud.sdk.OnLoginResultListener

object BqsVertification {
    fun switchVertify(name: String, cId: String, phone: String, activity: AppCompatActivity, type: Int, listener: OnLoginResultListener) {
        val params = BqsParams()
        params.setName(name)//必填，姓名
        params.setCertNo(cId)//必填，身份证号
        params.setMobile(phone)//必填，⼿机号
        params.setPartnerId(Constants.BQS_PARTNER_ID)//必填，商户编号

        params.setThemeColor(activity.resources.getColor(R.color.colorAccent))//选填，标题背景⾊，按钮背景⾊
        params.setForeColor(Color.WHITE)///选填，字体颜⾊、图标颜⾊
        params.setFontColor(Color.BLACK)///选填，字体颜⾊，运营商h5表单标题字体
        params.setBackIndicatorImage(null)//选填，返回按钮图标
        params.setProgressBarColor(activity.resources.getColor(R.color.colorAccent))//选填，进度条颜⾊
        params.setPageConfigMap(null)//选填，颜⾊配置map

        BqsCrawlerCloudSDK.setParams(params)

        BqsCrawlerCloudSDK.setFromActivity(activity)

        BqsCrawlerCloudSDK.setOnLoginResultListener(object : OnLoginResultListener {
            override fun onLoginSuccess(serviceId: Int) {
                listener.onLoginSuccess(serviceId)
            }

            override fun onLoginFailure(resultCode: String, resultDesc: String, serviceId: Int) {
                listener.onLoginFailure(resultCode, resultDesc, serviceId)
            }
        })

        when (type) {
            0 -> BqsCrawlerCloudSDK.loginMno()   //运营商验证
            1 -> BqsCrawlerCloudSDK.loginZm()    //芝麻分
        }

    }
}