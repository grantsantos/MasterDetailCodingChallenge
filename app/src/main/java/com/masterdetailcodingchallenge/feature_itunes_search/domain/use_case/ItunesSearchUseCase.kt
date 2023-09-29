package com.masterdetailcodingchallenge.feature_itunes_search.domain.use_case

import androidx.paging.PagingData
import com.masterdetailcodingchallenge.feature_itunes_search.domain.model.Result
import com.masterdetailcodingchallenge.feature_itunes_search.domain.repository.ItunesSearchRepository
import kotlinx.coroutines.flow.Flow

class ItunesSearchUseCase(
    private val repository: ItunesSearchRepository
) {

    fun execute() : Flow<PagingData<Result>> {
        return repository.getResultPagingData().flow
    }

}