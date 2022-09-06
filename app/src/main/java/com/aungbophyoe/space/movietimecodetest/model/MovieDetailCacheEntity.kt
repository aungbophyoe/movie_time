package com.aungbophyoe.space.movietimecodetest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail")
data class MovieDetailCacheEntity(
    val genres: String,
    @PrimaryKey
    val id: Int,
    val overview: String,
    val poster_path: String,
    val release_date: String,
    val runtime: String,
    val status: String,
    val title: String,
    val vote_average: String,
    val vote_count: Int,
    var fav:Boolean = false
)