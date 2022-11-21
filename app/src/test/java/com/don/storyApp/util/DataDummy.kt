package com.don.storyApp.util

import com.don.storyApp.domain.model.Story


/**
 * Created by gideon on 13 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
object DataDummy {
    fun generateDummyStories(): List<Story> {
        val storyList = ArrayList<Story>()
        for (i in 0..10) {
            val news = Story(
                id = "$i",
                photoUrl = "",
                createdAt = "",
                name = "STORY $i",
                lat = (60.0 + i),
                lon = (150.0 + i)
            )
            storyList.add(news)
        }
        return storyList
    }
}