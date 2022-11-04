package com.don.storyApp.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


/**
 * Created by gideon on 05 November 2022
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */
@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)
    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getRemoteKeysId(id: String): RemoteKeys?
    @Query("DELETE FROM remote_keys")
    suspend fun deleteRemoteKeys()
}