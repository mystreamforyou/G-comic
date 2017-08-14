package com.jack.g_comic.ui.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jack.g_comic.R
import com.jack.g_comic.domain.model.News
import com.jack.g_comic.domain.network.NewsDetailSource
import com.jack.g_comic.kits.recycler.AnotherBinder
import com.jack.g_comic.kits.recycler.AnotherViewHolder
import com.jack.g_comic.ui.WebDetailDialog
import kotlinx.android.synthetic.main.item_news.view.*

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

class NewsBinder : AnotherBinder<News>() {
    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): AnotherViewHolder {
        val view = inflater.inflate(R.layout.item_news, parent, false)
        return AnotherViewHolder(view)
    }

    override fun renderView(holder: AnotherViewHolder, itemView: View, item: News) {
        itemView.tvTitle.text = item.title
        itemView.tvTime.text = item.createdTime
        if (holder.adapterPosition % 2 == 0) {
            itemView.container.setBackgroundResource(R.color.alpha_grey)
        }else{
            itemView.container.setBackgroundResource(R.color.material_white)
        }

        itemView.container.setOnClickListener {
            WebDetailDialog(itemView.context, item, NewsDetailSource())
        }
    }

}