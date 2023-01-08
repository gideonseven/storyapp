package com.don.storyApp.data.local.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.don.storyApp.domain.model.Story


/**
 * Created by gideon on 05 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<Story>)

    @Query("SELECT * FROM story")
    fun getStories(): PagingSource<Int, Story>

    @Query("SELECT * FROM story")
    suspend fun getMarkers(): List<Story>

    @Query("DELETE FROM story")
    suspend fun deleteAll()
}