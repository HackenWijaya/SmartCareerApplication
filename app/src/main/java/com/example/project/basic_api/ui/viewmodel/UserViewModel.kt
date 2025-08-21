package com.example.project.basic_api.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.project.basic_api.data.model.UserResponse
import com.example.project.basic_api.data.repository.UserRepository
import com.example.project.basic_api.utils.NetworkUtils
import com.example.project.basic_api.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.delay

class UserViewModel(private val repository: UserRepository): ViewModel() {
    private val _data = MutableLiveData<Resource<List<UserResponse>>>()
    val data: LiveData<Resource<List<UserResponse>>> = _data

    fun getUsers(context: Context, forceRefresh: Boolean = false)  {
        if (_data.value == null || forceRefresh){
            if (NetworkUtils.isNetworkAvailable(context)){
                viewModelScope.launch {

                    try{
                        _data.value = Resource.Loading()
                        kotlinx.coroutines.delay(3000)
                        val response = repository.fetchUsers()
                        if (response.isEmpty()){
                            _data.postValue(Resource.Empty("no data found"))
                        } else{
                            _data.postValue(Resource.Success(response))
                        }

                    }catch (e: Exception){
                       _data.postValue(Resource.Error("unknown error: ${e.message}"))
                    }
                }

            }else{
                _data.postValue(Resource.Error("no internet cnnection"))
            }
        }
    }
}