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
import com.jack.g_comic.domain.network.CoverSource
import com.jack.g_comic.kits.recycler.AnotherAdapter
import com.jack.g_comic.log
import com.jack.g_comic.ui.activity.ComicActivity
import com.jack.g_comic.ui.binder.CoverBinder
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.ArrayList

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

class HomeFragment : Fragment() {
    companion object {
        val AIM_URL = "http://ishuhui.net/?PageIndex=1"
    }

    var mData = ArrayList<Cover>()

    lateinit var coverList: RecyclerView

    lateinit var homeRefresh: SwipeRefreshLayout

    lateinit var adapter: AnotherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        log("onCreateView")
        return inflater?.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log("onViewCreated")
        initView(view)
    }

    /**
     * init setting view
     */
    private fun initView(view: View) {
        homeRefresh = view.homeRefresh
        coverList = view.homeList

        coverList.layoutManager = GridLayoutManager(context, 2)

        adapter = AnotherAdapter()
                .with(Cover::class.java, CoverBinder().clickWith {
                    item, _ ->
                    jump2Comic(item)
                })
        coverList.adapter = adapter

        homeRefresh.setOnRefreshListener {
            load()
        }
        homeRefresh.post { homeRefresh.isRefreshing = true }
    }

    private fun jump2Comic(cover: Cover) {
        val intent = Intent(context, ComicActivity().javaClass)
        intent.putExtra(ComicActivity.INTENT_COMIC_URL, cover.link)
        startActivity(intent)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && mData.size == 0) {
            load()
        }

    }

    private fun load() {
        doAsync {
            val data = CoverSource().obtain(AIM_URL)

            uiThread {
                mData = data
                adapter.update(data)
                homeRefresh.isRefreshing = false
            }
        }
    }

}