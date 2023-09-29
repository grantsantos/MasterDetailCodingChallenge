package com.masterdetailcodingchallenge.feature_itunes_search.domain.use_case

import com.masterdetailcodingchallenge.feature_itunes_search.domain.model.Result
import com.masterdetailcodingchallenge.feature_itunes_search.domain.repository.ItunesSearchRepository

class UpdateFavoriteStatusUseCase(
    private val repository: ItunesSearchRepository
) {
    suspend fun execute(result: Result) {
        repository.updateFavoriteStatus(result.trackId!!, result.isFavorite)
    }
}