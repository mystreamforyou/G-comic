package com.jack.g_comic.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.jack.g_comic.R
import com.jack.g_comic.domain.model.Comic
import com.jack.g_comic.domain.network.ComicSource
import com.jack.g_comic.ui.fragment.ComicFragment
import kotlinx.android.synthetic.main.activity_comic.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/11
 **/

class ComicActivity : AppCompatActivity() {
    companion object {
        val INTENT_COMIC_URL = "url"
    }

    lateinit var adapter: ComicPagerAdapter
    var mData = ArrayList<Comic>()
    lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic)

        url = intent.getStringExtra(INTENT_COMIC_URL)
        adapter = ComicPagerAdapter(mData, supportFragmentManager)
        comicPagers.adapter = adapter
        comicPagers.offscreenPageLimit = 2

    }

    override fun onResume() {
        super.onResume()
        doAsync {
            val data = ComicSource().obtain(url)
            mData = data

            uiThread {
                adapter.refreshData(data)
            }
        }
    }

    inner class ComicPagerAdapter(var data: ArrayList<Comic> = ArrayList<Comic>(), fragmentManager: FragmentManager) :
            FragmentPagerAdapter(fragmentManager) {
        override fun getCount(): Int = data.size

        override fun getItem(position: Int): Fragment? = newInstance(data[position].comicUrl)

        fun refreshData(newData: ArrayList<Comic>) {
            data = newData
            notifyDataSetChanged()
        }

        fun newInstance(url: String) = ComicFragment.instance(url)
    }
}
