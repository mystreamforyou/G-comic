package com.jack.g_comic.ui.binder

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jack.g_comic.R
import com.jack.g_comic.domain.model.News
import com.jack.g_comic.domain.model.NewsContainer
import com.jack.g_comic.kits.recycler.AnotherAdapter
import com.jack.g_comic.kits.recycler.AnotherBinder
import com.jack.g_comic.kits.recycler.AnotherViewHolder
import kotlinx.android.synthetic.main.item_news_container.view.*

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

class NewsContainerBinder : AnotherBinder<NewsContainer>() {
    private val pool = RecyclerView.RecycledViewPool()

    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): AnotherViewHolder {
        val itemView = inflater.inflate(R.layout.item_news_container, parent, false)
        return AnotherViewHolder(itemView)
    }

    override fun renderView(holder: AnotherViewHolder, itemView: View, item: NewsContainer) {
        itemView.tvTitle.text = item.title
        itemView.newsList.isNestedScrollingEnabled = false
        val newsCount = item.newsList.size
        val params = itemView.newsList.layoutParams
        params.height = (newsCount * itemView.resources.displayMetrics.density * 40).toInt()
        itemView.newsList.layoutParams = params

        var adapter = itemView.newsList.adapter
        if (adapter is AnotherAdapter) {
            adapter.update(item.newsList)
        } else {
            adapter = AnotherAdapter().with(News::class.java, NewsBinder())
            itemView.newsList.adapter = adapter
            adapter.update(item.newsList)
        }

        val layoutManager = itemView.newsList.layoutManager
        layoutManager ?: let {
            itemView.newsList.layoutManager = LinearLayoutManager(itemView.context)
        }
    }
}