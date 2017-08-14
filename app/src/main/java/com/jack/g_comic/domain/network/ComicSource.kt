package com.jack.g_comic.domain.network

import com.jack.g_comic.domain.model.Comic
import com.jack.g_comic.getHtml
import org.jsoup.Jsoup
import java.util.*

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

class ComicSource : Source<ArrayList<Comic>> {
    override fun obtain(url: String): ArrayList<Comic> {
        val html = getHtml(url)
        val doc = Jsoup.parse(html)

        val elements = doc.select("div.mangaContentMainImg").select("img")
        val list = ArrayList<Comic>()

        for (element in elements) {
            var comicUrl: String
            val temp = element.attr("src")
            if (temp.contains(".png") || temp.contains(".jpg") || temp.contains(".JPEG")) {
                comicUrl = temp
            } else if (!"".equals(element.attr("data-original"))) {
                comicUrl = element.attr("data-original")
            } else {
                continue
            }

            val comic = Comic(comicUrl)
            list.add(comic)
        }
        return list
    }
}