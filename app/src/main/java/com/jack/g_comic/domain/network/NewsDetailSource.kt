package com.jack.g_comic.domain.network

import com.jack.g_comic.getHtml
import org.jsoup.Jsoup

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

class NewsDetailSource : Source<String> {
    override fun obtain(url: String): String {
        val html = getHtml(url)
        val doc = Jsoup.parse(html)

        val contentHtml =
                "<html>${doc.select("head")}<body>${doc.select("div.featureContentText")}</body></html>"
        return contentHtml
    }

}