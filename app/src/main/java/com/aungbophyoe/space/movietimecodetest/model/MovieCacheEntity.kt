package com.aungbophyoe.space.movietimecodetest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieCacheEntity(
    @PrimaryKey
    val id: Int,
    val overview: String,
    val poster_path: String,
    val title: String,
    val vote_average: String,
    var isUpcoming : Boolean,
    var isPopular : Boolean
)