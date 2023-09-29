package com.masterdetailcodingchallenge.feature_itunes_search.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.masterdetailcodingchallenge.common.data.local.database.AppDatabase
import com.masterdetailcodingchallenge.common.data.remote.api.ItunesSearchApi
import com.masterdetailcodingchallenge.common.data.remote.model.ItunesSearchResponse
import com.masterdetailcodingchallenge.feature_itunes_search.data.remote.ItunesSearchRemoteMediator
import com.masterdetailcodingchallenge.feature_itunes_search.domain.model.Result
import com.masterdetailcodingchallenge.feature_itunes_search.domain.repository.ItunesSearchRepository
import javax.inject.Inject

class ItunesSearchRepositoryImpl @Inject constructor(
    private val api: ItunesSearchApi,
    private val db: AppDatabase
) : ItunesSearchRepository {

    private val dao = db.itunesSearchDao

    override suspend fun searchItunes(queryMap: Map<String, String>): ItunesSearchResponse {
        return api.search(queryMap)
    }

    override suspend fun insertResults(results: List<Result>) {
        dao.insertResults(results)
    }

    override suspend fun deleteAllResults() {
        dao.deleteAllResults()
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getResultPagingData(): Pager<Int, Result> {
        return Pager(
            config = PagingConfig(pageSize = 15),
            remoteMediator = ItunesSearchRemoteMediator(api = api, db = db),
            pagingSourceFactory = {
                db.itunesSearchDao.getResultPagingSource()
            }
        )
    }

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        dao.updateFavoriteStatus(id, isFavorite)
    }

}