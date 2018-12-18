package com.aib.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings

import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.NetworkUtils
import com.wm.loan.R


import com.wm.loan.databinding.ActivityPayWebBinding

/**
 * 支付Web页面
 */
class PayWebActivity : BaseActivity<ActivityPayWebBinding>() {

    private var isFirst = true

    override fun getResId(): Int = R.layout.activity_pay_web

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        binding.presenter = Presenter()

        val bundle = intent.extras
        val url = bundle!!.getString("URL") //URL
        val params = bundle.getString("WEB_PARAMS")   //支付参数
        val order_num = bundle.getString("ORDER_NUM")   //支付参数

        binding.wv.loadUrl(url)

        val settings = binding.wv.settings
        settings.javaScriptEnabled = true

        // 设置 缓存模式
        if (NetworkUtils.isConnected()) {
            settings.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        // 把图片加载放在最后来加载渲染
        settings.blockNetworkImage = false
        // 支持多窗口
        settings.setSupportMultipleWindows(true)
        // 开启 DOM storage API 功能
        settings.domStorageEnabled = true
        // 开启 Application Caches 功能
        settings.setAppCacheEnabled(true)

        binding.wv.webViewClient = object : android.webkit.WebViewClient() {
            override fun onPageFinished(view: android.webkit.WebView, url: String) {
                super.onPageFinished(view, url)
                if (isFirst) {
                    sendInfoToJs(params, order_num)
                    isFirst = false
                }
            }

            override fun shouldOverrideUrlLoading(view: android.webkit.WebView, s: String): Boolean {
                if (s.startsWith("weixin://wap/pay?")) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.data = Uri.parse(s)
                    startActivity(intent)
                    return true
                }
                if (s.startsWith("alipays://platformapi/startApp?")) {
                    val intent = Intent()
                    intent.action = Intent.ACTION_VIEW
                    intent.data = Uri.parse(s)
                    startActivity(intent)
                    return true
                }
                return super.shouldOverrideUrlLoading(view, url)
            }
        }
    }

    fun sendInfoToJs(params: String?, order_num: String?) {
        binding.wv.evaluateJavascript("javascript:creditAlipay.getUrl('$params','$order_num')") {

        }
    }

    /***
     * 防止WebView加载内存泄漏
     */
    override fun onDestroy() {
        super.onDestroy()
        binding.wv.removeAllViews()
        binding.wv.destroy()
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }
    }
}
