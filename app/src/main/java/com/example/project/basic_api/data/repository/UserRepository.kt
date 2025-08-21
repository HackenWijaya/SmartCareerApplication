package com.example.project.basic_api.data.repository

import com.example.project.basic_api.data.model.UserResponse
import com.example.project.basic_api.data.network.ApiService

class UserRepository (private val api: ApiService){
    suspend fun fetchUsers(): List<UserResponse>{return api.getUsers()}

}