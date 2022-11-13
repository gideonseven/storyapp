package com.don.storyApp.util

import com.don.storyApp.domain.model.Story


/**
 * Created by gideon on 13 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
object DataDummy {
    fun generateDummyNewsEntity(): List<Story> {
        val storyList = ArrayList<Story>()
        for (i in 0..10) {
            val news = Story(
                "$i",
                "",
                "",
                "STORY $i",
            )
            storyList.add(news)
        }
        return storyList
    }
}