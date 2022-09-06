package com.aungbophyoe.space.movietimecodetest.data

import androidx.room.*
import com.aungbophyoe.space.movietimecodetest.model.MovieCacheEntity

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieCacheEntity: MovieCacheEntity)

    @Delete
    suspend fun deleteAll(movieCacheEntity: MovieCacheEntity)

    @Query("SELECT * from movies where isPopular = 1")
    suspend fun getAllPopularMovie() : List<MovieCacheEntity>

    @Query("SELECT * from movies where isUpcoming = 1")
    suspend fun getAllUpcomingMovie() : List<MovieCacheEntity>
}