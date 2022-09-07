package com.aungbophyoe.space.movietimecodetest.model

data class MovieResponse(
    val page: Int,
    val results: List<MovieCacheEntity>,
    val total_pages: Int,
    val total_results: Int
)