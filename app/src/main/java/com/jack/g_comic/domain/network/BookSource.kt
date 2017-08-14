package com.jack.g_comic.domain.network

import com.jack.g_comic.domain.model.Cover
import com.jack.g_comic.getHtml
import org.jsoup.Jsoup
import java.util.*

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

class BookSource : Source<ArrayList<Cover>> {
    override fun obtain(url: String): ArrayList<Cover> {
        val list = ArrayList<Cover>()

        val html = getHtml(url)
        val doc = Jsoup.parse(html)

        val elements = doc.select("ul.chinaMangaContentList").select("li")

        for (element in elements) {
            val coverUrl = if (element.select("img").attr("src").contains("http")) {
                element.select("img").attr("src")
            } else {
                "http://ishuhui.net" + element.select("img").attr("src")
            }

            val title = element.select("p").text()
            val link = "http://ishuhui.net" + element.select("div.chinaMangaContentImg").select("a").attr("href")

            val cover = Cover(coverUrl, title, link)
            list.add(cover)
        }

        return list
    }

}