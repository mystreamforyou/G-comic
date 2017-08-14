package com.jack.g_comic.ui

import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.webkit.WebSettings
import com.jack.g_comic.R
import com.jack.g_comic.domain.model.News
import com.jack.g_comic.domain.network.NewsDetailSource
import com.jack.g_comic.domain.network.Source
import com.jack.g_comic.load
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

class WebDetailDialog(val context: Context, val news: News, val source: Source<String>) {
    val dialog = BottomSheetDialog(context)

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_web_detail, null)
        val webView = view.find<ScrollWebView>(R.id.webView)
        val titleBar = view.find<Toolbar>(R.id.titleBar)
        val refresh = view.find<SwipeRefreshLayout>(R.id.newsDetailRefresh)

        titleBar.subtitle = news.title

        webView.settings.textZoom = 80
        webView.settings.displayZoomControls = true
        webView.settings.setSupportZoom(true)
        webView.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
//        webView.bottomListener = { isScrollToBottom: Boolean -> if (isScrollToBottom) dialog.dismiss() }

        dialog.setContentView(view)

        refresh.post { refresh.isRefreshing = true }


        doAsync  {
            val html = NewsDetailSource().obtain(news.link)
            uiThread {
                webView.load(html)
                refresh.isRefreshing = false
            }
        }

        refresh.setOnRefreshListener {
            doAsync {
                val html = source.obtain(news.link)
                uiThread {
                    webView.load(html)
                    refresh.isRefreshing = false
                }
            }
        }

        dialog.show()

    }
}