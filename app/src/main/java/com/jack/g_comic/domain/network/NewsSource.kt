package com.jack.g_comic.domain.network

import com.jack.g_comic.domain.model.News
import com.jack.g_comic.domain.model.NewsContainer
import com.jack.g_comic.getHtml
import org.jsoup.Jsoup
import java.util.ArrayList

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

class NewsSource : Source<ArrayList<NewsContainer>> {
    override fun obtain(url: String): ArrayList<NewsContainer> {
        val list = ArrayList<NewsContainer>()

        val html = getHtml(url)
        val doc = Jsoup.parse(html)

        val elements = doc.select("div.reportersBox").select("div.reportersMain")
        for (element in elements) {
            val title = element.select("div.mangeListTitle").select("span").text()
            val newsList = ArrayList<News>()

            for (ele in element.select("ul.reportersList").select("li").select("a")) {
                val title_news = ele.select("span")[0].text().replace("&amp", "\t")
                val createdTime = ele.select("span")[1].text()
                val link = "http://ishuhui.net/" + ele.attr("href")
                val news = News(title_news, createdTime, link)
                newsList.add(news)
            }

            val newsContainer = NewsContainer(title, newsList)

            list.add(newsContainer)
        }

        return list

    }

}