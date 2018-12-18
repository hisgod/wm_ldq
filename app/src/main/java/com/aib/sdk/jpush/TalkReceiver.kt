package com.aib.sdk.jpush

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.aib.utils.Constants
import com.aib.entity.JpushExtrasEntity
import com.aib.view.activity.WebViewActivity
import com.blankj.utilcode.util.ActivityUtils
import com.google.gson.Gson
import cn.jpush.android.api.JPushInterface

/**
 * 自定义接收器
 *
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
class TalkReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            val bundle = intent.extras
            //当用户点击通知栏的时候
            if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {
                val extras = bundle!!.getString(JPushInterface.EXTRA_EXTRA)
                val entity = Gson().fromJson(extras, JpushExtrasEntity::class.java)
                val args = Bundle()
                args.putString(Constants.URL, entity.reportURL)
                ActivityUtils.startActivity(args, WebViewActivity::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}
