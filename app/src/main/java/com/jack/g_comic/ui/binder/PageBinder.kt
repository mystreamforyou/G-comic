package com.jack.g_comic.ui.binder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jack.g_comic.R
import com.jack.g_comic.domain.model.Page
import com.jack.g_comic.kits.recycler.AnotherBinder
import com.jack.g_comic.kits.recycler.AnotherViewHolder
import kotlinx.android.synthetic.main.item_page.view.*

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

class PageBinder : AnotherBinder<Page>() {
    override fun createViewHolder(inflater: LayoutInflater, parent: ViewGroup): AnotherViewHolder {
        val itemView = inflater.inflate(R.layout.item_page, parent, false)
        return AnotherViewHolder(itemView)
    }

    override fun renderView(holder: AnotherViewHolder, itemView: View, item: Page) {
        itemView.tvPage.text = item.title
    }
}