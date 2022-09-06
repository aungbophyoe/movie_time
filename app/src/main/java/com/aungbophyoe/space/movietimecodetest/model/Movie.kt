package com.aungbophyoe.space.movietimecodetest.model

data class Movie(
    val id: Int,
    val overview: String,
    val poster_path: String,
    val title: String,
    val vote_average: Double,
    var isUpcoming : Boolean = false,
    var isPopular : Boolean = false
)