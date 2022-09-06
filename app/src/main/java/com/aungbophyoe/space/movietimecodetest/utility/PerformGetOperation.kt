package com.aungbophyoe.space.movietimecodetest.utility

import com.aungbophyoe.space.movietimecodetest.mapper.CacheMapper
import com.aungbophyoe.space.movietimecodetest.mapper.NetworkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T, A> performGetOperation(cacheMapper: CacheMapper,
                               networkMapper: NetworkMapper,
                               databaseQuery: suspend () -> List<T>,
                               networkCall: suspend () -> Response<List<T>>,
                               saveCallResult: suspend (T) -> Unit): Flow<DataState<List<T>>> = flow {
    /*emit(DataState.Loading)
    val source = databaseQuery.invoke()
    emit(DataState.Success(source))
    try {
        val response = networkCall.invoke()
        if(response.isSuccessful){
            val networkData = response.body()!!
            val dataList = networkMapper.mapFromEntityList(networkData as List<PhotoNetworkEntity>)
            for(data in dataList){
             saveCallResult(cacheMapper.mapToEntity(data))
            }
        }
    }catch (e:Exception){
        emit(DataState.Error(e))
        emit(DataState.Success(source))
    }*/
}