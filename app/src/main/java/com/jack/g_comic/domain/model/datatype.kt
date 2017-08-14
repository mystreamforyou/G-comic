package com.jack.g_comic.domain.model

/**
 * Description:
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/11
 **/

data class Cover(val coverUrl: String, val title: String, val link: String)

data class News(val title: String, val createdTime: String, val link: String)

data class NewsContainer(val title: String, val newsList: List<News>)

data class BookInfo(val updateTime: String, val description: String)

data class Page(val title: String, val link: String)

data class BookDetail(val pages: List<Page>, val info: BookInfo) {
    operator fun get(position: Int) = pages[position]
    val size
        get() = pages.size
}

data class Comic(val comicUrl: String)