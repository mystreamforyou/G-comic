package com.jack.g_comic.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.jack.g_comic.R
import com.jack.g_comic.domain.model.BookDetail
import com.jack.g_comic.domain.model.News
import com.jack.g_comic.domain.model.Page
import com.jack.g_comic.domain.network.BookDetailSource
import com.jack.g_comic.domain.network.SBSSource
import com.jack.g_comic.kits.recycler.AnotherAdapter
import com.jack.g_comic.snackbar
import com.jack.g_comic.ui.WebDetailDialog
import com.jack.g_comic.ui.binder.PageBinder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.activity_book_detail.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import java.util.*

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/11
 **/

class BookDetailActivity : AppCompatActivity() {
    lateinit var url: String
    lateinit var pageList: RecyclerView
    lateinit var adapter: AnotherAdapter
    lateinit var pageRefresh: SwipeRefreshLayout
    lateinit var bookDetail: BookDetail

    companion object {
        val INTENT_COVER_URL = "cover"
        val INTENT_URL = "url"
        val INTENT_TITLE = "title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        setSupportActionBar(toolbar)

        init()
    }

    private fun init() {
        val coverUrl = intent.getStringExtra(INTENT_COVER_URL)
        val title = intent.getStringExtra(INTENT_TITLE)
        url = intent.getStringExtra(INTENT_URL)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.title = title
        Picasso.with(this).load(coverUrl).into(backgroundImage)

        pageRefresh = find(R.id.pageRefresh)
        pageRefresh.setOnRefreshListener { load() }


        pageList = find(R.id.pageList)
        pageList.layoutManager = GridLayoutManager(this, 4)

        //TODO need to do better
        adapter = AnotherAdapter().with(Page::class.java, PageBinder().clickWith { _, position ->
            if (title.contains("SBS")) {
                val news = News(bookDetail[position].title, "", bookDetail[position].link)
                WebDetailDialog(this, news, SBSSource())
            } else
                jump2Read(position)
        })

        pageList.adapter = adapter

    }


    override fun onResume() {
        super.onResume()
        pageRefresh.post { pageRefresh.isRefreshing = true }
        load()
    }

    private fun load() = doAsync {
        bookDetail = BookDetailSource().obtain(url)
        val data = bookDetail.pages as ArrayList<Page>

        uiThread {
            adapter.update(data)
            pageRefresh.isRefreshing = false
            if (bookDetail.size == 0) {
                showError()
            }
        }
    }

    /**
     * to show error
     */
    private fun showError() {
        pageList.snackbar(R.string.page_load_error)
    }


    /**
     * jump to comic page
     * and special handle SBS
     */
    private fun jump2Read(position: Int) {
        val intent = Intent(this, ComicActivity().javaClass)
        intent.putExtra(ComicActivity.INTENT_COMIC_URL, bookDetail[position].link)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_book_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == android.R.id.home) {
            onBackPressed()
            return true
        } else if (id == R.id.action_info) {
            showBookInfo()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * to show the info of book
     */
    private fun showBookInfo() {
        val bookInfo = bookDetail.info
        pageList.snackbar(bookInfo.description + "\n" + bookInfo.updateTime)
    }
}