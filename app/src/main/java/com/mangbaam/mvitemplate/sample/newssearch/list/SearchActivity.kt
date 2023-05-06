package com.mangbaam.mvitemplate.sample.newssearch.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.mangbaam.mvitemplate.R
import com.mangbaam.mvitemplate.base.MviActivity
import com.mangbaam.mvitemplate.databinding.ActivitySearchBinding

class SearchActivity :
    MviActivity<ActivitySearchBinding, SearchState, SearchEvent, SearchSideEffect>(
        R.layout.activity_search,
    ) {
    override val viewModel: SearchViewModel by viewModels()
    private val adapter = SearchListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() = with(binding) {
        rvArticles.adapter = adapter
        btnSearch.setOnClickListener { viewModel.search(etSearch.text.toString()) }
        btnRetry.setOnClickListener { viewModel.search() }
    }

    override fun render(state: SearchState) = with(binding) {
        // set visibility
        tvMessage.isVisible = !state.loading && (state.error != null || state.empty)
        btnRetry.isVisible = !state.loading && state.error != null
        tvLatestKeyword.isVisible = state.latestKeyword.isNotBlank()
        rvArticles.isVisible = state.error == null
        pbLoading.isVisible = state.loading

        // set text
        tvLatestKeyword.text = getString(R.string.latest_keyword, state.latestKeyword)
        tvMessage.text = when {
            state.error != null -> state.error
            state.latestKeyword.isBlank() && state.empty -> getString(R.string.search_hint)
            state.latestKeyword.isNotBlank() && state.empty -> getString(R.string.no_result)
            else -> ""
        }
        adapter.submitList(state.result)
    }

    override fun handleEvent(event: SearchEvent) {
        TODO("Not yet implemented")
    }

    override fun handleSideEffect(sideEffect: SearchSideEffect) {
        TODO("Not yet implemented")
    }
}
