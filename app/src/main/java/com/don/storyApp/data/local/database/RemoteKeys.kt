package com.don.storyApp.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by gideon on 05 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?
)