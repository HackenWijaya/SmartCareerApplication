package com.example.project.basic_api.data.network


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
object RetrofitInstance {
    private const val BASE_URL_JSONPLACHEHOLDER = "https://jsonplaceholder.typicode.com"
    private const val BASE_URL_CRUDAPI = "https://crudapi.co.uk/api/v1/"
    private const val BASE_URL = "https://newsapi.org/v2/"

        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    fun getJsonPlaceholderApi(): ApiService{
        return Retrofit.Builder()
            .baseUrl(BASE_URL_JSONPLACHEHOLDER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    fun getCrudApi(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_CRUDAPI)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
    fun getApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

}