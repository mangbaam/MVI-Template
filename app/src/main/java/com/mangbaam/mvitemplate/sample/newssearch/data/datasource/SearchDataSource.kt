package com.mangbaam.mvitemplate.sample.newssearch.data.datasource

import com.mangbaam.mvitemplate.sample.newssearch.data.network.Api.PAGE_SIZE
import com.mangbaam.mvitemplate.sample.newssearch.data.network.ApiService
import com.mangbaam.mvitemplate.sample.newssearch.model.Article
import com.mangbaam.mvitemplate.sample.newssearch.model.Result
import com.mangbaam.mvitemplate.sample.newssearch.model.mapper.toArticle

class SearchDataSource(private val service: ApiService) {
    operator fun invoke(
        api: String,
        keyword: String,
        searchIn: String,
        sortBy: String,
        page: Int,
    ): Result<List<Article>> {
        val response = service.search(
            api = api,
            keyword = keyword,
            searchIn = searchIn,
            sortBy = sortBy,
            pageSize = PAGE_SIZE,
            page = page,
        )
        return if (response.isSuccessful) {
            Result.Success(response.body()?.articles?.map { it.toArticle() } ?: emptyList())
        } else {
            Result.Error(response.message())
        }
    }
}
