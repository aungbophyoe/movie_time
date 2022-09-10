package com.aungbophyoe.space.movietimecodetest.network

import com.aungbophyoe.space.movietimecodetest.model.MovieDetailNetworkEntity
import com.aungbophyoe.space.movietimecodetest.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("popular")
    suspend fun  getAllPopularMovies(@Query("api_key") apiKey : String, @Query("page") page : Int): MovieResponse

    @GET("upcoming")
    suspend fun getAllUpComingMovies(@Query("api_key") apiKey : String, @Query("page") page : Int): MovieResponse

    @GET("{id}")
    suspend fun getMovieDetails(@Path("id")id:String,@Query("api_key") apiKey: String) : Response<MovieDetailNetworkEntity>
}