package com.mangbaam.mvitemplate.sample.newssearch.data.repository

import com.mangbaam.mvitemplate.sample.newssearch.data.datasource.SearchDataSource
import com.mangbaam.mvitemplate.sample.newssearch.model.Article
import com.mangbaam.mvitemplate.sample.newssearch.model.Result
import com.mangbaam.mvitemplate.sample.newssearch.model.SearchOption

class NewsRepository(private val dataSource: SearchDataSource) {
    fun search(searchOption: SearchOption): Result<List<Article>> {
        return dataSource(
            api = "4022e4f8b6a04afa8774c7ccca5bcc6b",
            keyword = searchOption.keyword,
            searchIn = searchOption.searchIn.joinToString { it.path },
            sortBy = searchOption.sortBy.path,
            page = searchOption.page,
        )
    }
}
