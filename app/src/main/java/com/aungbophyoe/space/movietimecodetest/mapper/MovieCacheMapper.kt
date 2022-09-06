package com.aungbophyoe.space.movietimecodetest.mapper

import com.aungbophyoe.space.movietimecodetest.model.Movie
import com.aungbophyoe.space.movietimecodetest.model.MovieCacheEntity
import javax.inject.Inject

class MovieCacheMapper @Inject constructor() : EntityMapper<MovieCacheEntity,Movie> {
    override fun mapFromEntity(entity: MovieCacheEntity): Movie {
        return Movie(entity.id,entity.overview,entity.poster_path,entity.title,entity.vote_average.toDouble(),entity.isUpcoming,entity.isPopular)
    }

    override fun mapToEntity(domainModel: Movie): MovieCacheEntity {
        return MovieCacheEntity(domainModel.id,domainModel.overview,domainModel.poster_path,domainModel.title,domainModel.vote_average.toString(),domainModel.isUpcoming,domainModel.isPopular)
    }

    fun mapFromEntityList(entities: List<MovieCacheEntity>) : List<Movie>{
        return entities.map { mapFromEntity(it) }
    }

    fun mapToEntityList(movies:List<Movie>):List<MovieCacheEntity> {
        return movies.map { mapToEntity(it) }
    }
}