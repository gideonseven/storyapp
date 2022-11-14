package com.don.storyApp.data.local.database

import com.don.storyApp.domain.model.Story


/**
 * Created by gideon on 14 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
class FakeDao {
    private var listStory = arrayListOf<Story>()

    fun insertStory(story: List<Story>) {
        listStory.addAll(story)
    }

    fun getStories() = listStory

    fun deleteAll() {
        listStory.clear()
    }
}