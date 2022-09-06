package com.aungbophyoe.space.movietimecodetest.utility

import android.content.Context
import android.net.ConnectivityManager

object Constants {
    const val DatabaseName = "MoviesDatabase"

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}