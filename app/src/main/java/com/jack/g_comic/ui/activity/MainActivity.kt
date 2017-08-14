package com.jack.g_comic.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.jack.g_comic.R
import com.jack.g_comic.ui.adapter.ContentPagerAdapter
import com.jack.g_comic.ui.fragment.BookFragment
import com.jack.g_comic.ui.fragment.HomeFragment
import com.jack.g_comic.ui.fragment.NewsFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.*

/**
 * Description:
 * Copyright  : Copyright (c) 2015
 * Author     : liujianguang
 * Date       : 2017/8/11
 **/

class MainActivity : AppCompatActivity() {

    companion object {
        val GITHUB_URL = "https://github.com/wuapnjie/PoiShuhui-Kotlin"
    }

    val nameResList: ArrayList<Int> = arrayListOf(R.string.tab_one, R.string.tab_two, R.string.tab_three)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()


    }

    private fun init() {
        setSupportActionBar(toolbar)
        fab.setOnClickListener { jump2MyGithub() }
        val fragments = ArrayList<Fragment>()


        fragments.add(HomeFragment())
        fragments.add(BookFragment())
        fragments.add(NewsFragment())


        val nameList = nameResList.map(this::getString)

        viewPager.adapter = ContentPagerAdapter(fragments, nameList, supportFragmentManager)
        viewPager.offscreenPageLimit = 2

        tabLayout.setupWithViewPager(viewPager)

        //        Picasso.with(this).setIndicatorsEnabled(true)
    }

    private fun jump2MyGithub() {
        val uri = Uri.parse(GITHUB_URL);
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.action_about) {
            val intent = Intent(this, AboutActivity().javaClass)
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
