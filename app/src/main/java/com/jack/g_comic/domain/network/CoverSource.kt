package com.jack.g_comic.domain.network

import com.jack.g_comic.domain.model.Cover
import com.jack.g_comic.getHtml
import org.jsoup.Jsoup
import java.util.ArrayList

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

class CoverSource : Source<ArrayList<Cover>> {
    override fun obtain(url: String): ArrayList<Cover> {
        val list = ArrayList<Cover>()

        val html = getHtml(url)
        val doc = Jsoup.parse(html)

        val elements = doc.select("ul.mangeListBox").select("li")
        for (element in elements) {
            val coverUrl = element.select("img").attr("src")
            val title = element.select("h1").text() + "\n" + element.select("h2").text()
            val link = "http://ishuhui.net" + element.select("div.magesPhoto").select("a").attr("href")
            val cover = Cover(coverUrl, title, link)
            list.add(cover)
        }

        return list
    }

}