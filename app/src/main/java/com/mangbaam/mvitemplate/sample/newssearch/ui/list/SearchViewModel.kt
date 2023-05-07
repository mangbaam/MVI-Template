package com.mangbaam.mvitemplate.sample.newssearch.ui.list

import androidx.lifecycle.SavedStateHandle
import com.mangbaam.mvitemplate.base.MviViewModel
import com.mangbaam.mvitemplate.sample.newssearch.data.repository.NewsRepository
import com.mangbaam.mvitemplate.sample.newssearch.model.Result
import com.mangbaam.mvitemplate.sample.newssearch.model.SortBy
import kotlinx.coroutines.delay

class SearchViewModel(
    savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository,
) :
    MviViewModel<SearchState, SearchEvent, SearchSideEffect>(
        SearchState(),
        savedStateHandle,
    ) {

    fun search() = intent {
        reduce { state.copy(loading = true, result = emptyList()) }
        delay(3000)
        when (val result = newsRepository.search(state.searchOption)) {
            is Result.Success -> {
                reduce { state.copy(loading = false, result = result.data) }
            }
            is Result.Error -> {
                reduce { state.copy(loading = false, error = result.message) }
            }
        }
    }

    fun changeSortBy(sortBy: SortBy) = intent {
        reduce { state.copy(searchOption = searchOption.copy(sortBy = sortBy)) }
    }
}
