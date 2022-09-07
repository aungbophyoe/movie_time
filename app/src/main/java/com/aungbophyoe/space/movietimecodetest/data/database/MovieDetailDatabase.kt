package com.aungbophyoe.space.movietimecodetest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aungbophyoe.space.movietimecodetest.data.MovieDao
import com.aungbophyoe.space.movietimecodetest.data.MovieDetailDao
import com.aungbophyoe.space.movietimecodetest.model.MovieCacheEntity
import com.aungbophyoe.space.movietimecodetest.model.MovieDetailCacheEntity

@Database(entities = [MovieDetailCacheEntity::class, MovieCacheEntity::class],version = 1,exportSchema = false)
abstract class MovieDetailDatabase : RoomDatabase() {
    companion object {
        const val DatabaseName = "movieDetailDatabase"
    }

    abstract fun getMovieDetailDao() : MovieDetailDao
    abstract fun getMovieDao() : MovieDao
}