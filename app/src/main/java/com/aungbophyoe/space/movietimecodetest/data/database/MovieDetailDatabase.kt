package com.aungbophyoe.space.movietimecodetest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aungbophyoe.space.movietimecodetest.data.dao.MovieDao
import com.aungbophyoe.space.movietimecodetest.data.dao.MovieDetailDao
import com.aungbophyoe.space.movietimecodetest.data.dao.PopularMovieRemoteKeysDao
import com.aungbophyoe.space.movietimecodetest.data.dao.RemoteKeysDao
import com.aungbophyoe.space.movietimecodetest.model.MovieCacheEntity
import com.aungbophyoe.space.movietimecodetest.model.MovieDetailCacheEntity
import com.aungbophyoe.space.movietimecodetest.model.PopularMovieRemoteKeys
import com.aungbophyoe.space.movietimecodetest.model.RemoteKeys

@Database(entities = [MovieDetailCacheEntity::class,
    MovieCacheEntity::class, RemoteKeys::class, PopularMovieRemoteKeys::class],version = 1,exportSchema = false)
abstract class MovieDetailDatabase : RoomDatabase() {
    companion object {
        const val DatabaseName = "movieDetailDatabase"
    }

    abstract fun getMovieDetailDao() : MovieDetailDao
    abstract fun getMovieDao() : MovieDao
    abstract fun geRemoteKeysDao() : RemoteKeysDao
    abstract fun getPopularMovieRemoteKeysDao() : PopularMovieRemoteKeysDao
}