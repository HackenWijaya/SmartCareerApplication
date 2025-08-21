package com.example.project.basic_api.data.network
import com.example.project.basic_api.data.model.NewsResponse
import com.example.project.basic_api.data.model.ProductPostRequest
import com.example.project.basic_api.data.model.ProductResponse
import com.example.project.basic_api.data.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): NewsResponse
    @GET("users")
    suspend fun getUsers(): List<UserResponse>
    @POST("product")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body products: List<ProductPostRequest>,
    ): ProductResponse

    @GET("product")
    suspend fun getProducts(
        @Header("Authorization") token: String
    ): ProductResponse
}