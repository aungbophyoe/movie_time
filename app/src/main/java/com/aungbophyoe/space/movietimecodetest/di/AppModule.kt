package com.aungbophyoe.space.movietimecodetest.di

import android.content.Context
import androidx.room.Room
import com.aungbophyoe.space.movietimecodetest.BuildConfig
import com.aungbophyoe.space.movietimecodetest.data.MovieDao
import com.aungbophyoe.space.movietimecodetest.data.MovieDetailDao
import com.aungbophyoe.space.movietimecodetest.data.database.MovieDetailDatabase
import com.aungbophyoe.space.movietimecodetest.mapper.CacheMapper
import com.aungbophyoe.space.movietimecodetest.mapper.MovieCacheMapper
import com.aungbophyoe.space.movietimecodetest.mapper.NetworkMapper
import com.aungbophyoe.space.movietimecodetest.network.ApiService
import com.aungbophyoe.space.movietimecodetest.reporistory.MainRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun moshi() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()!!

    @Provides
    fun provideGsonBuilder() : Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }
    @Provides
    fun provideRetrofitBuilder(moshi: Moshi) : Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
    }

    @Provides
    fun provideApiService(retrofit: Retrofit.Builder) : ApiService {
        return retrofit
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    fun provideMainRepository(@ApplicationContext context: Context, apiService: ApiService,
                              movieDetailDao: MovieDetailDao,movieDao: MovieDao, cacheMapper: CacheMapper,
                              networkMapper: NetworkMapper,movieCacheMapper: MovieCacheMapper
    ): MainRepository {
        return MainRepository(context,apiService,movieDetailDao,movieDao,cacheMapper,networkMapper,movieCacheMapper)
    }

    @Provides
    fun provideMovieDetailDatabase(@ApplicationContext context: Context):MovieDetailDatabase{
        return Room.databaseBuilder(context,MovieDetailDatabase::class.java,MovieDetailDatabase.DatabaseName)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideMovieDetailDao(movieDetailDatabase: MovieDetailDatabase):MovieDetailDao{
        return movieDetailDatabase.getMovieDetailDao()
    }

    @Provides
    fun provideMovieDao(movieDetailDatabase: MovieDetailDatabase):MovieDao{
        return movieDetailDatabase.getMovieDao()
    }
}