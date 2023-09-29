package com.masterdetailcodingchallenge.feature_itunes_search.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.masterdetailcodingchallenge.common.data.local.database.AppDatabase
import com.masterdetailcodingchallenge.common.data.remote.api.ItunesSearchApi
import com.masterdetailcodingchallenge.feature_itunes_search.domain.model.Result
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ItunesSearchRemoteMediator(
    private val api: ItunesSearchApi,
    private val db: AppDatabase
) : RemoteMediator<Int, Result>() {


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Result>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    1 //just return the first page for this case because there is no pagination in the api
                }
            }

            val queryMap = HashMap<String, String>()
            queryMap["term"] = "star"
            queryMap["amp;country"] = "au"
            queryMap["amp;media"] = "movie"
            queryMap["amp;all"] = ""


            val newResults = api.search(queryMap).results.map {
                it.toResultDomain()
            }




            db.withTransaction {
                val oldResults = db.itunesSearchDao.getAllResults()

                val resultsTobeInserted = newResults.filterIndexed { index, result ->
                    val isFavoriteOld = oldResults.getOrNull(index)
                    if (isFavoriteOld != null) {
                        isFavoriteOld.isFavorite && result.isFavorite
                    } else {
                        true
                    }
                }

                db.itunesSearchDao.insertResults(resultsTobeInserted)
            }

            MediatorResult.Success(
                endOfPaginationReached = true //just return true for this case because there is no pagination in the api
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}