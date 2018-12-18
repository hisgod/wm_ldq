package com.aib.service

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.graphics.Color

import com.aib.sdk.album.AlbumMediaLoader
import com.baidu.mobstat.StatService
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig

import com.wm.loan.R
import cn.jpush.android.api.JPushInterface
import com.aib.BaseApplication
import com.bqs.crawler.cloud.sdk.BqsCrawlerCloudSDK

class InitializeService : IntentService("INIT_WORK") {
    private val ctx: Context?

    init {
        ctx = BaseApplication.context
    }

    @SuppressLint("MissingPermission")
    override fun onHandleIntent(intent: Intent?) {
        //utilcode
        Utils.init(ctx!!)

        //album
        Album.initialize(AlbumConfig.newBuilder(ctx)
                .setAlbumLoader(AlbumMediaLoader())
                .build())

        //极光推送
        JPushInterface.init(ctx)

        //百度统计
        StatService.autoTrace(ctx, true, false)

        //白骑士
        BqsCrawlerCloudSDK.initialize(ctx)
    }
}
