package com.aungbophyoe.space.movietimecodetest.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aungbophyoe.space.movietimecodetest.model.PopularMovieRemoteKeys

@Dao
interface PopularMovieRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemote(list: List<PopularMovieRemoteKeys>)

    @Query("SELECT * FROM popular_movie_remoteKey WHERE movieId = :id")
    suspend fun getPopularMovieRemoteKeys(id:String) : PopularMovieRemoteKeys

    @Query("DELETE FROM popular_movie_remoteKey")
    suspend fun clearAll()
}