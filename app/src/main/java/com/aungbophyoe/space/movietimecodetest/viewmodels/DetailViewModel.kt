package com.aungbophyoe.space.movietimecodetest.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aungbophyoe.space.movietimecodetest.model.MovieDetail
import com.aungbophyoe.space.movietimecodetest.reporistory.MainRepository
import com.aungbophyoe.space.movietimecodetest.utility.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel@Inject constructor(private val repository: MainRepository): ViewModel() {
    private val _data : MutableLiveData<DataState<MovieDetail?>> = MutableLiveData()
    val data : MutableLiveData<DataState<MovieDetail?>> get() = _data
    fun getMovieDetail(id:Int){
        viewModelScope.launch {
            repository.getMovieDetail(id)
                .onEach {
                    _data.value = it
                    Log.d("response","success")
                }
                .launchIn(viewModelScope)
        }
    }

    fun setFavFlag(id: Int,flag:Boolean){
        viewModelScope.launch {
            repository.setFlagFav(id,flag)
        }
    }
}