package com.masterdetailcodingchallenge.common.data.remote.model

import com.masterdetailcodingchallenge.feature_itunes_search.domain.model.Result

data class ResultDto(
    val amgArtistId: Int?,
    val artistId: Int?,
    val artistName: String?,
    val artistViewUrl: String?,
    val artworkUrl100: String?,
    val artworkUrl30: String?,
    val artworkUrl60: String?,
    val collectionArtistId: Int?,
    val collectionArtistName: String?,
    val collectionArtistViewUrl: String?,
    val collectionCensoredName: String?,
    val collectionExplicitness: String?,
    val collectionHdPrice: Double?,
    val collectionId: Int?,
    val collectionName: String?,
    val collectionPrice: Double?,
    val collectionViewUrl: String?,
    val contentAdvisoryRating: String?,
    val country: String?,
    val currency: String?,
    val description: String?,
    val discCount: Int?,
    val discNumber: Int?,
    val hasITunesExtras: Boolean?,
    val isStreamable: Boolean?,
    val kind: String?,
    val longDescription: String?,
    val previewUrl: String?,
    val primaryGenreName: String?,
    val releaseDate: String?,
    val shortDescription: String?,
    val trackCensoredName: String?,
    val trackCount: Int?,
    val trackExplicitness: String?,
    val trackHdPrice: Double?,
    val trackHdRentalPrice: Double?,
    val trackId: Int?,
    val trackName: String?,
    val trackNumber: Int?,
    val trackPrice: Double?,
    val trackRentalPrice: Double?,
    val trackTimeMillis: Int?,
    val trackViewUrl: String?,
    val wrapperType: String?
) {
    fun toResultDomain() : Result {
        val name = if (isAudioBook()) {
            collectionName
        } else {
            trackName
        }
        val type = if (isAudioBook()) {
            "Audio Book"
        } else {
            when (kind) {
                "feature-movie" -> {
                    "Movie"
                }
                "song" -> {
                    "Song"
                }
                else -> {
                    ""
                }
            }
        }
        val price = if (isAudioBook()) {
            collectionPrice
        } else {
            trackPrice
        }
        return Result(
            imageUrl = artworkUrl100.orEmpty(),
            title = name.orEmpty(),
            artistName = artistName.orEmpty(),
            trackId = trackId,
            type = type,
            primaryGenreName = primaryGenreName.orEmpty(),
            price = price ?: 0.0,
            releaseDate = releaseDate.orEmpty(),
            longDescription = longDescription ?: description.orEmpty(),
            contentAdvisoryRating = contentAdvisoryRating.orEmpty(),
        )
    }

    private fun isAudioBook() : Boolean {
        return wrapperType == "audiobook"
    }
}