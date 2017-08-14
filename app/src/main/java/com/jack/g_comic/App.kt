package com.jack.g_comic

import android.app.Application
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val client = OkHttpClient()
        val picasso = Picasso.Builder(this)
                .downloader(OkHttp3Downloader(client))
                .build()

        Picasso.setSingletonInstance(picasso)
    }
}