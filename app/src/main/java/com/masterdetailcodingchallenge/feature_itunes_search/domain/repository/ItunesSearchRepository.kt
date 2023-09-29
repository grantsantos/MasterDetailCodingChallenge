package com.masterdetailcodingchallenge.feature_itunes_search.domain.repository

import androidx.paging.Pager
import com.masterdetailcodingchallenge.common.data.remote.model.ItunesSearchResponse
import com.masterdetailcodingchallenge.feature_itunes_search.domain.model.Result

interface ItunesSearchRepository {

    suspend fun searchItunes(
        queryMap: Map<String, String>
    ): ItunesSearchResponse

    suspend fun insertResults(results: List<Result>)

    suspend fun deleteAllResults()

    fun getResultPagingData(): Pager<Int, Result>

    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

}