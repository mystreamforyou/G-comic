package com.jack.g_comic.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/11
 **/

class ContentPagerAdapter(
        val fragments: List<Fragment>,
        val nameList: List<String>,
        fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment? {
        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? = nameList[position]

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        super.destroyItem(container, position, `object`)
    }
}