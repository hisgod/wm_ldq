package com.aib.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

import com.aib.utils.Constants
import com.aib.viewmodel.WebVm
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ThreadUtils


import com.wm.loan.R
import com.wm.loan.databinding.ActivityWebViewBinding

/**
 * 普通Web页面
 */
class WebViewActivity : BaseActivity<ActivityWebViewBinding>() {

    override fun getResId(): Int = R.layout.activity_web_view

    override fun initData(savedInstanceState: Bundle?) {

        binding.tb.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)

        val vm = getViewModel(WebVm::class.java)

        binding.presenter = Presenter()

        val bundle = intent.extras
        val url = bundle!!.getString(Constants.URL) //URL
        val loanId = bundle.getInt(Constants.LOAN_ID)  //贷款平台ID
        val loanTitle = bundle.getString(Constants.LOAN_TITLE)  //贷款标题

        if (!TextUtils.isEmpty(loanTitle)) {
            binding.tvTitle.text = loanTitle
        }

        //统计点击量
        if (loanId != 0) {
            ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<String>() {
                override fun onSuccess(result: String?) {
                    vm.addLoanClick(loanId, result!!)
                }

                override fun doInBackground(): String? = SPUtils.getInstance().getString(Constants.TOKEN)
            })
        }

        binding.wv.loadUrl(url)

        binding.wv.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(webView: WebView, i: Int) {
                super.onProgressChanged(webView, i)
                if (i == 100) {
                    binding.pb.visibility = View.GONE
                } else {
                    binding.pb.visibility = View.VISIBLE
                }
                binding.pb.progress = i
            }
        }

        binding.wv.webViewClient = WebViewClient()

        val settings = binding.wv.settings
        settings.javaScriptEnabled = true

        // 设置 缓存模式
        if (NetworkUtils.isConnected()) {
            settings.cacheMode = android.webkit.WebSettings.LOAD_DEFAULT
        } else {
            settings.cacheMode = android.webkit.WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        // 把图片加载放在最后来加载渲染
        settings.blockNetworkImage = false
        // 支持多窗口
        settings.setSupportMultipleWindows(true)
        // 开启 DOM storage API 功能
        settings.domStorageEnabled = true
        // 开启 Application Caches 功能
        settings.setAppCacheEnabled(true)
        //设置支持大小
        settings.setSupportZoom(true)

        /**
         * 检测下载
         */
        binding.wv.setDownloadListener { s, s1, s2, s3, l ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse(s)
            startActivity(intent)
        }
    }

    //设置返回键动作（防止按返回键直接退出程序)
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (binding.wv.canGoBack()) {//当webview不是处于第一页面时，返回上一个页面
                binding.wv.goBack()
                return true
            } else {//当webview处于第一页面时,直接退出程序
                finish()
            }
        }
        return super.onKeyDown(keyCode, event)
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
