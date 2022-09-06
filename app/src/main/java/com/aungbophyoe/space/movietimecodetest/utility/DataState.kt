package com.aungbophyoe.space.movietimecodetest.utility

sealed class DataState<out R> {
    data class Success<out T>(val data:T):DataState<T>()
    data class Error<out T>(val exception:Exception):DataState<T>()
    object TryAgain:DataState<Nothing>()
    object Loading: DataState<Nothing>()
}
