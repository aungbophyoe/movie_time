package com.aungbophyoe.space.movietimecodetest.model

data class MovieDetailNetworkEntity(
    val genres: List<Genre>,
    val id: Int,
    val overview: String,
    var poster_path: String?,
    val release_date: String,
    val runtime: Int,
    val status: String,
    val title: String,
    val vote_average: Double,
    val vote_count: Int
) {
    data class Genre(
        val id: Int,
        val name: String
    )
}