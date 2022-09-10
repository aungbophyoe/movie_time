package com.aungbophyoe.space.movietimecodetest.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.aungbophyoe.space.movietimecodetest.model.MovieCacheEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<MovieCacheEntity>)

    @Query("DELETE FROM movies")
    suspend fun clearAllMovie()

    @Query("SELECT * FROM movies where isUpcoming = 1")
    fun getAllUpComingMovie() : PagingSource<Int, MovieCacheEntity>

    @Query("SELECT * FROM movies where isPopular = 1")
    fun getAllPopularMovies() : PagingSource<Int, MovieCacheEntity>
}