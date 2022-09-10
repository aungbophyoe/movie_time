package com.aungbophyoe.space.movietimecodetest.reporistory
import android.content.Context
import android.util.Log
import com.aungbophyoe.space.movietimecodetest.BuildConfig
import com.aungbophyoe.space.movietimecodetest.data.dao.MovieDao
import com.aungbophyoe.space.movietimecodetest.data.dao.MovieDetailDao
import com.aungbophyoe.space.movietimecodetest.mapper.CacheMapper
import com.aungbophyoe.space.movietimecodetest.mapper.NetworkMapper
import com.aungbophyoe.space.movietimecodetest.model.MovieCacheEntity
import com.aungbophyoe.space.movietimecodetest.model.MovieDetail
import com.aungbophyoe.space.movietimecodetest.network.ApiService
import com.aungbophyoe.space.movietimecodetest.utility.Constants.isNetworkAvailable
import com.aungbophyoe.space.movietimecodetest.utility.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val context: Context,
    private val apiService: ApiService,
    private val movieDetailDao: MovieDetailDao,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper
) {
    suspend fun setFlagFav(id: Int,flag:Boolean){
        try {
            movieDetailDao.updateFav(flag,id)
        }catch (e:Exception){

        }
    }

    suspend fun getMovieDetail(id:Int) : Flow<DataState<MovieDetail?>> = flow {
        emit(DataState.Loading)
        try {
            if(isNetworkAvailable(context)){
                val updateData = movieDetailDao.getMovieDetail(id)
                if(updateData!=null){
                    emit(DataState.Success(cacheMapper.mapFromEntity(updateData)))
                }
                emit(DataState.Loading)
                val response = apiService.getMovieDetails(id.toString(),BuildConfig.AUTH_TOKEN)
                if(response.isSuccessful){
                    val networkData = response.body()
                    val flag = movieDetailDao.getFavFlag(id)
                    val data = networkMapper.mapFromEntity(networkData)
                    data.fav = flag ?: false
                    movieDetailDao.insertAll(cacheMapper.mapToEntity(data))
                    val updateData = movieDetailDao.getMovieDetail(id)
                    emit(DataState.Success(cacheMapper.mapFromEntity(updateData)))
                }else{
                    if(updateData!=null){
                        emit(DataState.Success(cacheMapper.mapFromEntity(updateData)))
                    }
                }
            }else{
                val updateData = movieDetailDao.getMovieDetail(id)
                if(updateData!=null){
                    emit(DataState.Success(cacheMapper.mapFromEntity(updateData)))
                }else{
                    emit(DataState.TryAgain)
                }
            }
        }catch (e:Exception){
            Log.d("api","$e")
            emit(DataState.Error(e))
        }
    }
}