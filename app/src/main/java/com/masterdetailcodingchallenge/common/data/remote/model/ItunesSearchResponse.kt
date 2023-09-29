package com.masterdetailcodingchallenge.common.data.remote.model

data class ItunesSearchResponse(
    val resultCount: Int,
    val results: List<ResultDto>
)