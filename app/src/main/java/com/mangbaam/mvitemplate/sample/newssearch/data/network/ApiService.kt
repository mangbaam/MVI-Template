package com.mangbaam.mvitemplate.sample.newssearch.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mangbaam.mvitemplate.sample.newssearch.data.ArticlesDto
import com.mangbaam.mvitemplate.sample.newssearch.data.network.Api.BASE_URL
import com.mangbaam.mvitemplate.sample.newssearch.data.network.Api.EVERYTHING
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(EVERYTHING)
    fun search(
        @Query("apiKey") api: String,
        @Query("q") keyword: String,
        @Query("searchIn") searchIn: String,
        @Query("sortBy") sortBy: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
    ): Response<ArticlesDto>

    companion object {
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
                .build()
        }
        val service get() = retrofit.create(ApiService::class.java)
    }
}
