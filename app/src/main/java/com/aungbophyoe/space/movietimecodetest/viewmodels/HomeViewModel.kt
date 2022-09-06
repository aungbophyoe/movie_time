package com.aungbophyoe.space.movietimecodetest.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aungbophyoe.space.movietimecodetest.model.Movie
import com.aungbophyoe.space.movietimecodetest.reporistory.MainRepository
import com.aungbophyoe.space.movietimecodetest.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: MainRepository):ViewModel() {
    init {
        //getData()
        //getUpComingMovieData()
    }
    private val _data : MutableLiveData<DataState<List<Movie>>> = MutableLiveData()
    val data : MutableLiveData<DataState<List<Movie>>> get() = _data
    fun getData(){
        viewModelScope.launch {
            repository.getAllPopularMovies()
                .onEach {
                    _data.value = it
                    Log.d("response","success")
                }
                .launchIn(viewModelScope)
        }
    }

    private val _upComingMovieData : MutableLiveData<DataState<List<Movie>>> = MutableLiveData()
    val upComingMovieData : MutableLiveData<DataState<List<Movie>>> get() = _upComingMovieData
    fun getUpComingMovieData(){
        viewModelScope.launch {
            delay(1000)
            repository.getAllUpComingMovies()
                .onEach {
                    _upComingMovieData.value = it
                }
                .launchIn(viewModelScope)
        }
    }
}