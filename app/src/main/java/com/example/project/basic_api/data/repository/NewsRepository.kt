package com.example.project.basic_api.data.repository

import com.example.project.basic_api.data.model.NewsResponse
import com.example.project.basic_api.data.network.ApiService

class NewsRepository(private val apiService: ApiService) {

    suspend fun fetchNews(
        query: String,
        from: String,
        to: String,
        sortBy: String,
        apiKey: String
    ): NewsResponse {
        return apiService.getNews(query, from, to, sortBy, apiKey)
    }
}