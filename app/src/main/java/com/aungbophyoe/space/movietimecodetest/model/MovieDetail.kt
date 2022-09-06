package com.aungbophyoe.space.movietimecodetest.model

data class MovieDetail(
    val genres: String,
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