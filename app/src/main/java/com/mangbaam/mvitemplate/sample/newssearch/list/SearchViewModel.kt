package com.mangbaam.mvitemplate.sample.newssearch.list

import androidx.lifecycle.SavedStateHandle
import com.mangbaam.mvitemplate.base.MviViewModel
import com.mangbaam.mvitemplate.sample.newssearch.Article
import kotlinx.coroutines.delay

class SearchViewModel(savedStateHandle: SavedStateHandle) :
    MviViewModel<SearchState, SearchEvent, SearchSideEffect>(
        SearchState(),
        savedStateHandle,
    ) {

    fun search(keyword: String) = intent {
        reduce { state.copy(loading = true, latestKeyword = keyword, result = emptyList()) }
        delay(3000)
        val result = emptyList<Article>()
        reduce { state.copy(loading = false, result = result) }
    }

    fun search() {
        search(state.latestKeyword)
    }
}
