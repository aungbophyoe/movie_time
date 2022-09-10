package com.aungbophyoe.space.movietimecodetest.model

import com.squareup.moshi.Json

data class Movie(
    val id: Int,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    val title: String,
    @Json(name = "vote_average")
    val voteAverage: Double?,
    var isUpcoming : Boolean = false,
    var isPopular : Boolean = false
)