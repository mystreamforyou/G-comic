package com.jack.g_comic.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.jack.g_comic.R
import com.jack.g_comic.kits.common.toBundle
import com.jack.g_comic.log
import com.jack.g_comic.snackbar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

class ComicFragment : Fragment() {
    lateinit var progressBar: ProgressBar
    lateinit var iv_comic: ImageView
    lateinit var url: String;

    companion object {
        fun instance(url: String): ComicFragment {
            val fragment = ComicFragment()
            fragment.arguments =
                    mapOf("url" to url)
                            .toBundle()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments.getString("url", "")
        log("onCreate")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_comic_page, container, false)
        progressBar = rootView.find(R.id.progressBar)
        iv_comic = rootView.find(R.id.iv_comic)
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar.visibility = View.VISIBLE
    }


    override fun onResume() {
        super.onResume()
        Picasso.with(context)
                .load(url)
                .placeholder(R.color.material_deep_purple_50)
                .into(iv_comic, object : Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError() {
                        iv_comic.snackbar(R.string.network_error)
                    }
                })
    }

}

