package com.masterdetailcodingchallenge.common.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.masterdetailcodingchallenge.feature_itunes_search.domain.model.Result

@Dao
interface ItunesSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertResults(results: List<Result>)

    @Query("SELECT * FROM result_entity_tbl")
    fun getAllResults() : List<Result>

    @Query("DELETE FROM result_entity_tbl")
    fun deleteAllResults()

    @Query("SELECT * FROM result_entity_tbl")
    fun getResultPagingSource(): PagingSource<Int, Result>


    @Query("UPDATE result_entity_tbl SET isFavorite = :isFavorite WHERE trackId = :id")
    fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

}