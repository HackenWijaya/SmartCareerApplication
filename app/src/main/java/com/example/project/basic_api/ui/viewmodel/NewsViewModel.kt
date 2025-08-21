package com.example.project.basic_api.ui.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.project.basic_api.data.model.Article
import com.example.project.basic_api.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.Query

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

   fun getNews(query: String, from:String, to:String, sortBy:String, apiKey:String)=
       liveData(Dispatchers.IO) {
           try{
               val newsResponse = newsRepository.fetchNews(query, from, to, sortBy, apiKey)
               emit(newsResponse.articles)
           }catch (e: Exception){
               emit(emptyList())
           }
       }
}