package com.don.storyApp.util

import java.util.*


/**
 * Created by gideon on 30 October 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
internal object NumberGenerator {
    fun generate(max: Int): Int {
        val random = Random()
        return random.nextInt(max)
    }
}