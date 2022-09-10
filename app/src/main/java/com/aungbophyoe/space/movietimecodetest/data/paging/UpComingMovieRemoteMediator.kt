package com.aungbophyoe.space.movietimecodetest.data.paging

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.aungbophyoe.space.movietimecodetest.BuildConfig
import com.aungbophyoe.space.movietimecodetest.data.database.MovieDetailDatabase
import com.aungbophyoe.space.movietimecodetest.model.MovieCacheEntity
import com.aungbophyoe.space.movietimecodetest.model.RemoteKeys
import com.aungbophyoe.space.movietimecodetest.network.ApiService
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class UpComingMovieRemoteMediator constructor(
        private val database:MovieDetailDatabase,
        private val apiService: ApiService
)  : RemoteMediator<Int,MovieCacheEntity>() {
    private val STARTING_PAGE_INDEX = 1

    private val remoteKeysDao = database.geRemoteKeys()
    private val movieDao = database.getMovieDao()

    override suspend fun initialize(): InitializeAction {
       return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieCacheEntity>): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page  = when(pageKeyData){
            is MediatorResult.Success ->{
                return pageKeyData
            }else->{
                pageKeyData as Int
            }
        }

      try {
          delay(1000)
          val response = apiService.getAllUpComingMovies(BuildConfig.AUTH_TOKEN,page)
          if(response.isSuccessful){
              val data = response.body()!!.results
              val endOfList = data.isEmpty()
              val prevKey = if (page == STARTING_PAGE_INDEX) null else page-1
              val nextKey = if(endOfList) null else page+1
              database.withTransaction {
                  val list = data.map {
                      MovieCacheEntity(it.id.toString(),it.overview,it.posterPath,it.title,it.voteAverage,isUpcoming = true)
                  }
                  if(loadType == LoadType.REFRESH){
                      remoteKeysDao.clearAll()
                      movieDao.clearAllMovie()
                  }
                  val keys = list.map {
                      RemoteKeys(it.id,prevKey,nextKey)
                  }
                  remoteKeysDao.insertRemote(keys)
                  movieDao.insertAll(list)
                  Log.d("main","insert > $page")
              }
              return MediatorResult.Success(endOfPaginationReached = endOfList)
          }else{
              return MediatorResult.Success(endOfPaginationReached = true)
          }
      }catch (e:IOException){
          Log.d("main","error > ${e.message}")
       return   MediatorResult.Error(e)
      }catch (e:HttpException){
          Log.d("main","errorr > ${e.message}")
          return MediatorResult.Error(e)
      }
    }


    private suspend fun getKeyPageData(loadType: LoadType,state: PagingState<Int, MovieCacheEntity>) : Any{
        return when(loadType){
            LoadType.REFRESH->{
                val remoteKeys = getRefreshRemoteKey(state)
                remoteKeys?.nextKey?.minus(1)?:STARTING_PAGE_INDEX
            }
            LoadType.PREPEND->{
                val remoteKeys = getFirstRemoteKey(state)
               val prevKey = remoteKeys?.prevKey ?:MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevKey
            }
            LoadType.APPEND->{
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.nextKey ?:MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
        }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, MovieCacheEntity>) : RemoteKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie -> remoteKeysDao.getRemoteKeys(movie.id)}
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, MovieCacheEntity>) : RemoteKeys? {
        return state.pages
            .lastOrNull{it.data.isNotEmpty()}
            ?.data?.lastOrNull()
            ?.let { movie -> remoteKeysDao.getRemoteKeys(movie.id) }
    }

    private suspend fun getRefreshRemoteKey(state: PagingState<Int, MovieCacheEntity>) : RemoteKeys? {
        return state.anchorPosition?.let { position->
            state.closestItemToPosition(position)?.id?.let {repId->
                remoteKeysDao.getRemoteKeys(repId)
            }
        }
    }

}
