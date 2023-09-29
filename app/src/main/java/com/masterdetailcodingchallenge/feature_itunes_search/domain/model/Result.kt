package com.masterdetailcodingchallenge.feature_itunes_search.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "result_entity_tbl")
data class Result(
    @PrimaryKey val trackId: Int?,
    val imageUrl: String,
    val title: String,
    val artistName: String,
    val type: String,

    //For detail screen
    val primaryGenreName: String,
    val price: Double,
    val releaseDate: String,
    val longDescription: String,
    val contentAdvisoryRating: String,


    var isFavorite: Boolean = false
) : Parcelable