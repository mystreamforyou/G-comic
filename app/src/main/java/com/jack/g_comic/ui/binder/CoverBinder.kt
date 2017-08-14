package com.jack.g_comic.ui.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jack.g_comic.R
import com.jack.g_comic.domain.model.Cover
import com.jack.g_comic.kits.recycler.AnotherBinder
import com.jack.g_comic.kits.recycler.AnotherViewHolder
import com.jack.g_comic.loadUrl
import kotlinx.android.synthetic.main.item_cover.view.*

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/11
 **/

class CoverBinder : AnotherBinder<Cover>() {
    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): AnotherViewHolder {
        val view = inflater.inflate(R.layout.item_cover, parent, false)
        return AnotherViewHolder(view)
    }

    override fun renderView(holder: AnotherViewHolder, itemView: View, item: Cover) {
        itemView.tvCover.text = item.title
        itemView.ivCover.loadUrl(item.coverUrl)
    }
}