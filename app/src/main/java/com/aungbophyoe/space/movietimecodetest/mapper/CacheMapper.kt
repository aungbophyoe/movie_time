package com.aungbophyoe.space.movietimecodetest.mapper

import com.aungbophyoe.space.movietimecodetest.model.MovieDetail
import com.aungbophyoe.space.movietimecodetest.model.MovieDetailCacheEntity
import javax.inject.Inject

class CacheMapper @Inject constructor() : EntityMapper<MovieDetailCacheEntity,MovieDetail> {
    override fun mapFromEntity(entity: MovieDetailCacheEntity): MovieDetail {
        return MovieDetail(
            genres = entity.genres,
            id = entity.id,
            overview = entity.overview,
            poster_path = entity.poster_path,
            release_date = entity.release_date,
            runtime = entity.runtime,
            status = entity.status,
            vote_count = entity.vote_count,
            title = entity.title,
            vote_average = entity.vote_average,
            fav = entity.fav
        )
    }

    override fun mapToEntity(domainModel: MovieDetail): MovieDetailCacheEntity {
        return MovieDetailCacheEntity(
            genres = domainModel.genres,
            id = domainModel.id,
            overview = domainModel.overview,
            poster_path = domainModel.poster_path,
            release_date = domainModel.release_date,
            runtime = domainModel.runtime,
            status = domainModel.status,
            vote_count = domainModel.vote_count,
            title = domainModel.title,
            vote_average = domainModel.vote_average,
            fav = domainModel.fav
        )
    }
}