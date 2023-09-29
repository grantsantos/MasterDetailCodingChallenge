package com.masterdetailcodingchallenge.feature_itunes_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.masterdetailcodingchallenge.feature_itunes_search.domain.model.Result
import com.masterdetailcodingchallenge.feature_itunes_search.domain.use_case.AllUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItunesSearchViewModel @Inject constructor(
    private val useCase: AllUseCases
) : ViewModel() {

    val pagedResultLiveData = useCase.itunesSearchUseCase.execute().asLiveData().cachedIn(viewModelScope)

    fun updateFavoriteStatus(result: Result) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.updateFavoriteStatusUseCase.execute(result)
        }
    }

}