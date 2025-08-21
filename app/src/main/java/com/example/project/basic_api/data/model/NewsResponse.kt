package com.example.project.basic_api.data.model

data class NewsResponse(
    val articles: List<Article>
)
data class Article(
    val title: String,
    val urlToImage: String?,
    val url:String,
)


