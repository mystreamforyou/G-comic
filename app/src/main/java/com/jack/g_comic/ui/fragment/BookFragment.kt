package com.jack.g_comic.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jack.g_comic.R
import com.jack.g_comic.domain.model.Cover
import com.jack.g_comic.domain.network.BookSource
import com.jack.g_comic.kits.recycler.AnotherAdapter
import com.jack.g_comic.log
import com.jack.g_comic.ui.activity.BookDetailActivity
import com.jack.g_comic.ui.binder.CoverBinder
import kotlinx.android.synthetic.main.fragment_book.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Company    : 北京畅游天下网络科技有限公司
 * Author     : liujianguang
 * Date       : 2017/8/11
 **/

class BookFragment : Fragment() {
    companion object {
        val AIM_URL = "http://ishuhui.net/ComicBookList/"
    }

    var mData = ArrayList<Cover>()
    lateinit var adapter: AnotherAdapter
    lateinit var bookList: RecyclerView
    lateinit var bookRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)

    }

    /**
     * init setting views
     */
    private fun initView(view: View) {
        bookRefresh = view.bookRefresh
        bookList = view.bookList

        bookList.layoutManager = GridLayoutManager(context, 2)
        adapter = AnotherAdapter()
                .with(Cover::class.java, CoverBinder().clickWith { item, _ -> jump2Detail(item) })
        bookList.adapter = adapter

        bookRefresh.setOnRefreshListener {
            load()
        }
        bookRefresh.post { bookRefresh.isRefreshing = true }
    }

    /**
     * click event to detail activity
     */
    private fun jump2Detail(cover: Cover) {
        val intent = Intent(context, BookDetailActivity().javaClass)

        intent.putExtra(BookDetailActivity.INTENT_COVER_URL, cover.coverUrl)
        intent.putExtra(BookDetailActivity.INTENT_URL, cover.link)
        intent.putExtra(BookDetailActivity.INTENT_TITLE, cover.title)
        startActivity(intent)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        log("setUserVisibleHint")
        if (isVisibleToUser && mData.size == 0) {
            load()
        }

    }

    private fun load() {
        doAsync {
            val data = BookSource().obtain(AIM_URL)

            uiThread {
                mData = data
                adapter.update(data)
                bookRefresh.isRefreshing = false
            }
        }
    }

}