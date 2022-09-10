package com.aungbophyoe.space.movietimecodetest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movie_remoteKey")
class PopularMovieRemoteKeys(
    @PrimaryKey
    val movieId:String,
    val prevKey:Int?,
    val nextKey:Int?
) {
}