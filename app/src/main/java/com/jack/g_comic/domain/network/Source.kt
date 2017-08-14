package com.jack.g_comic.domain.network

/**
 * Description: G-comic
 * Copyright  : Copyright (c) 2017
 * Author     : liujianguang
 * Date       : 2017/8/14
 **/

interface Source<out T> {
    //使用out符号进行修饰，表示参数只能是被用作read，只能用在方法的返回值
    //使用in 符号修饰参数类型，表示只能被用作方法的参数，被操作，而不能作为返回值使用
    fun obtain(url: String): T
}