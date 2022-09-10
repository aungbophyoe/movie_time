package com.aungbophyoe.space.movietimecodetest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aungbophyoe.space.movietimecodetest.data.MovieDao
import com.aungbophyoe.space.movietimecodetest.data.MovieDetailDao
import com.aungbophyoe.space.movietimecodetest.data.RemoteKeysDao
import com.aungbophyoe.space.movietimecodetest.model.MovieCacheEntity
import com.aungbophyoe.space.movietimecodetest.model.MovieDetailCacheEntity
import com.aungbophyoe.space.movietimecodetest.model.RemoteKeys

@Database(entities = [MovieDetailCacheEntity::class, MovieCacheEntity::class, RemoteKeys::class],version = 1,exportSchema = false)
abstract class MovieDetailDatabase : RoomDatabase() {
    companion object {
        const val DatabaseName = "movieDetailDatabase"
    }

    abstract fun getMovieDetailDao() : MovieDetailDao
    abstract fun getMovieDao() : MovieDao
    abstract fun geRemoteKeys() : RemoteKeysDao
}