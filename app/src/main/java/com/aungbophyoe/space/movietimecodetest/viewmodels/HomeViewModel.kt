package com.aungbophyoe.space.movietimecodetest.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.aungbophyoe.space.movietimecodetest.data.database.MovieDetailDatabase
import com.aungbophyoe.space.movietimecodetest.paging.UpComingMovieRemoteMediator
import com.aungbophyoe.space.movietimecodetest.model.MovieCacheEntity
import com.aungbophyoe.space.movietimecodetest.network.ApiService
import com.aungbophyoe.space.movietimecodetest.paging.PopularMovieRemoteMediator
import com.aungbophyoe.space.movietimecodetest.reporistory.MainRepository
import com.aungbophyoe.space.movietimecodetest.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val database:MovieDetailDatabase,private val apiService: ApiService
):ViewModel() {

    @ExperimentalPagingApi
    fun getAllPopularMovies() : Flow<PagingData<MovieCacheEntity>> = Pager(
        config = PagingConfig(24,enablePlaceholders = false),
        pagingSourceFactory = {database.getMovieDao().getAllPopularMovies()},
        remoteMediator = PopularMovieRemoteMediator(database,apiService)
    ).flow.cachedIn(viewModelScope)

    @ExperimentalPagingApi
    fun getAllUpcomingMovies() : Flow<PagingData<MovieCacheEntity>> = Pager(
        config = PagingConfig(24,enablePlaceholders = false),
        pagingSourceFactory = {database.getMovieDao().getAllUpComingMovie()},
        remoteMediator = UpComingMovieRemoteMediator(database,apiService)
    ).flow.cachedIn(viewModelScope)

}