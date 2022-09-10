package com.aungbophyoe.space.movietimecodetest.data.dao

import androidx.room.*
import com.aungbophyoe.space.movietimecodetest.model.MovieDetailCacheEntity

@Dao
interface MovieDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movieDetailCacheEntity: MovieDetailCacheEntity)

    @Query("SELECT * from movie_detail WHERE id = :movieId")
    suspend fun getMovieDetail(movieId:Int) : MovieDetailCacheEntity

    @Query("UPDATE movie_detail SET fav =:flag WHERE id = :movieId")
    suspend fun updateFav(flag:Boolean,movieId: Int)

    @Query("SELECT fav from movie_detail where id = :movieId")
    suspend fun getFavFlag(movieId: Int):Boolean?

    @Delete
    suspend fun deleteMovieDetail(movieDetailCacheEntity: MovieDetailCacheEntity)
}