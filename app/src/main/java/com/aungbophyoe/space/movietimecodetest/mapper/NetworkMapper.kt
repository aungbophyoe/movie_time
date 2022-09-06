package com.aungbophyoe.space.movietimecodetest.mapper

import com.aungbophyoe.space.movietimecodetest.model.MovieDetail
import com.aungbophyoe.space.movietimecodetest.model.MovieDetailNetworkEntity
import javax.inject.Inject

class NetworkMapper @Inject constructor() : EntityMapper<MovieDetailNetworkEntity?,MovieDetail> {
    override fun mapFromEntity(entity: MovieDetailNetworkEntity?): MovieDetail {
        var genres = ""
        entity!!.genres?.forEach {
            genres += it.name.plus(", ")
        }
        return MovieDetail(id = entity.id, genres = genres, overview = entity.overview,
            poster_path = entity.poster_path, release_date = entity.release_date,
            runtime = entity.runtime.toString(), status = entity.status, title = entity.title,
            vote_average = entity.vote_average.toString(), vote_count = entity.vote_count, fav = false)
    }

    override fun mapToEntity(domainModel: MovieDetail): MovieDetailNetworkEntity? {
        return null
    }

}