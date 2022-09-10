package com.aungbophyoe.space.movietimecodetest.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remoteKey")
data class RemoteKeys(
    @PrimaryKey
    val repoId:String,
    val prevKey:Int?,
    val nextKey:Int?
)
