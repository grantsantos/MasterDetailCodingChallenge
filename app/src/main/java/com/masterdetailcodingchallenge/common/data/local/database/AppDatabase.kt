package com.masterdetailcodingchallenge.common.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.masterdetailcodingchallenge.common.data.local.dao.ItunesSearchDao
import com.masterdetailcodingchallenge.feature_itunes_search.domain.model.Result

@Database(
    entities = [
        Result::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val itunesSearchDao: ItunesSearchDao
}