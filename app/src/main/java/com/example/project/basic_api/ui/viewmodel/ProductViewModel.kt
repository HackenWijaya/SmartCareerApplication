package com.example.project.basic_api.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.basic_api.data.model.ProductPostRequest
import com.example.project.basic_api.data.model.ProductResponse
import com.example.project.basic_api.data.model.UserResponse
import com.example.project.basic_api.data.repository.ProductRepository
import com.example.project.basic_api.data.repository.UserRepository
import com.example.project.basic_api.utils.NetworkUtils
import com.example.project.basic_api.utils.Resource
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository): ViewModel() {
    private val _data = MutableLiveData<Resource<ProductResponse>>()
    val data: LiveData<Resource<ProductResponse>> = _data

    private val _createStatus = MutableLiveData<Resource<Unit>>()
    val createStatus : LiveData<Resource<Unit>> = _createStatus

    fun getProducts(context: Context, forceRefresh: Boolean = false)  {
        if (_data.value == null || forceRefresh){
            if (NetworkUtils.isNetworkAvailable(context)){
                viewModelScope.launch {

                    try{
                        _data.value = Resource.Loading()
                        kotlinx.coroutines.delay(3000)
                        val response = repository.fetchProduct()
                        if (response.items.isEmpty()){
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

    fun createProduct(context: Context, products: List<ProductPostRequest>)  {
            if (NetworkUtils.isNetworkAvailable(context)){
                viewModelScope.launch {
                    try{
                        _createStatus.value = Resource.Loading()

                        val response = repository.createProduct(products)
                        getProducts(context, forceRefresh = true)

                    }catch (e: Exception){
                        _createStatus.postValue(Resource.Error("unknown error: ${e.message}"))
                    }
                }

            }else{
                _createStatus.postValue(Resource.Error("no internet cnnection"))
            }
        }
    }
