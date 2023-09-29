package com.masterdetailcodingchallenge.common.data.remote.api

import com.masterdetailcodingchallenge.common.data.remote.model.ItunesSearchResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ItunesSearchApi {

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }

    @GET("search")
    suspend fun search(
        @QueryMap queryMap: Map<String, String>
    ): ItunesSearchResponse

}