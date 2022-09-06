package com.aungbophyoe.space.movietimecodetest.reporistory
import android.content.Context
import android.util.Log
import com.aungbophyoe.space.movietimecodetest.BuildConfig
import com.aungbophyoe.space.movietimecodetest.data.MovieDao
import com.aungbophyoe.space.movietimecodetest.data.MovieDetailDao
import com.aungbophyoe.space.movietimecodetest.mapper.CacheMapper
import com.aungbophyoe.space.movietimecodetest.mapper.MovieCacheMapper
import com.aungbophyoe.space.movietimecodetest.mapper.NetworkMapper
import com.aungbophyoe.space.movietimecodetest.model.Movie
import com.aungbophyoe.space.movietimecodetest.model.MovieDetail
import com.aungbophyoe.space.movietimecodetest.network.ApiService
import com.aungbophyoe.space.movietimecodetest.utility.Constants
import com.aungbophyoe.space.movietimecodetest.utility.Constants.isNetworkAvailable
import com.aungbophyoe.space.movietimecodetest.utility.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val context: Context,
    private val apiService: ApiService,
    private val movieDetailDao: MovieDetailDao,
    private val movieDao: MovieDao,
    private val cacheMapper: CacheMapper,
    private val networkMapper: NetworkMapper,
    private val movieCacheMapper: MovieCacheMapper
) {
    suspend fun getAllPopularMovies() : Flow<DataState<List<Movie>>> = flow {
        emit(DataState.Loading)
        kotlinx.coroutines.delay(1000)
        try {
            if(isNetworkAvailable(context)){
                val response = apiService.getAllPopularMovies(BuildConfig.AUTH_TOKEN,1)
                if(response.isSuccessful){
                    val networkData = response.body()
                    val data = movieCacheMapper.mapToEntityList(networkData!!.results)
                    data.map {
                        it.isPopular = true
                        movieDao.insertAll(it)
                    }
                    val dbData = movieDao.getAllPopularMovie()
                    emit(DataState.Success(movieCacheMapper.mapFromEntityList(dbData)))
                }
            }else{
                val dbData = movieDao.getAllPopularMovie()
                if(dbData!=null){
                    emit(DataState.Success(movieCacheMapper.mapFromEntityList(dbData)))
                }else{
                    emit(DataState.TryAgain)
                }
            }
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }

    suspend fun getAllUpComingMovies() : Flow<DataState<List<Movie>>> = flow {
        emit(DataState.Loading)
        kotlinx.coroutines.delay(1000)
        try {
            if(isNetworkAvailable(context)){
                val response = apiService.getAllUpComingMovies(BuildConfig.AUTH_TOKEN,1)
                if(response.isSuccessful){
                    val networkData = response.body()
                    val data = movieCacheMapper.mapToEntityList(networkData!!.results)
                    data.map {
                        it.isUpcoming = true
                        movieDao.insertAll(it)
                    }
                    val dbData = movieDao.getAllUpcomingMovie()
                    emit(DataState.Success(movieCacheMapper.mapFromEntityList(dbData)))
                }
            }else{
                val dbData = movieDao.getAllUpcomingMovie()
                if(dbData!=null){
                    emit(DataState.Success(movieCacheMapper.mapFromEntityList(dbData)))
                }else{
                    emit(DataState.TryAgain)
                }
            }
        }catch (e:Exception){
            emit(DataState.Error(e))
        }
    }

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
                kotlinx.coroutines.delay(1000)
                val response = apiService.getMovieDetails(id.toString(),BuildConfig.AUTH_TOKEN)
                if(response.isSuccessful){
                    val networkData = response.body()
                    val flag = movieDetailDao.getFavFlag(id)
                    val data = networkMapper.mapFromEntity(networkData)
                    data.fav = flag ?: false
                    movieDetailDao.insertAll(cacheMapper.mapToEntity(data))
                    val updateData = movieDetailDao.getMovieDetail(id)
                    emit(DataState.Success(cacheMapper.mapFromEntity(updateData)))
                }
            }else{
                val updateData = movieDetailDao.getMovieDetail(id)
                if(updateData!=null){
                    emit(DataState.Success(cacheMapper.mapFromEntity(updateData)))
                }else{
                    emit(DataState.TryAgain)
                }
            }
            /*val oldDbData = movieDetailDao.getMovieDetail(id)
            emit(DataState.Success(cacheMapper.mapFromEntity(oldDbData)))*/
        }catch (e:Exception){
            Log.d("api","$e")
            emit(DataState.Error(e))
        }
    }
}