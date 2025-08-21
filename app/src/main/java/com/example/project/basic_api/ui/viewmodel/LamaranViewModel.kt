package com.example.project.basic_api.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.basic_api.data.model.LamaranModel
import com.example.project.basic_api.data.repository.LamaranRepository
import com.example.project.basic_api.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class LamaranViewModel(private val repository: LamaranRepository) : ViewModel()  {

    private val _lamaranData = MutableLiveData<Resource<List<LamaranModel>>>()
    val lamaranData: LiveData<Resource<List<LamaranModel>>> = _lamaranData

    private val _addlamaranStatus = MutableLiveData<Resource<Unit>>()
    val addLamaranStatus: LiveData<Resource<Unit>> = _addlamaranStatus

    private val _updatelamaranStatus = MutableLiveData<Resource<Unit>>()
    val updateLamaranStatus: LiveData<Resource<Unit>> = _updatelamaranStatus

    private val _deletelamaranStatus = MutableLiveData<Resource<Unit>>()
    val deleteLamaranStatus: LiveData<Resource<Unit>> = _deletelamaranStatus

    private val _userStatus = MutableLiveData<Resource<String>>()
    val userStatus: LiveData<Resource<String>> = _userStatus


//    fun fetchLamaranData() {
//        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
//        _lamaranData.value = Resource.Loading()
//        viewModelScope.launch {
//            try {
//                val lamaranList = repository.getLamaranByUserId(userId)
//                if (lamaranList.isEmpty()) {
//                    _lamaranData.postValue(Resource.Empty("No lamaran data found."))
//                } else {
//                    _lamaranData.postValue(Resource.Success(lamaranList))
//                }
//            } catch (e: Exception) {
//                _lamaranData.postValue(Resource.Error("Error fetching lamaran: ${e.message}"))
//            }
//        }
//    }

    fun fetchLamaranByUserId() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        _lamaranData.value = Resource.Loading()
        viewModelScope.launch {
            val result = repository.getLamaranByUserId(userId)
            _lamaranData.postValue(result)
        }
    }

    // Fungsi untuk mengambil status berdasarkan UID
    fun getStatusByUid(uid: String) {
        _userStatus.value = Resource.Loading()
        viewModelScope.launch {
            _userStatus.value = repository.getStatusByUid(uid)
        }
    }

    // Fungsi untuk mengambil data Kelola
    fun getLamaran() {
        _lamaranData.value = Resource.Loading()
        viewModelScope.launch {
            _lamaranData.value = repository.getLamaran()
        }
    }

    // Fungsi untuk menambahkan Kelola baru
    fun addLamaran(lamaran: LamaranModel) {
        _addlamaranStatus.value = Resource.Loading()
        viewModelScope.launch {
            _addlamaranStatus.value = repository.addLamaran(lamaran)
        }
    }

    // Fungsi untuk update data Kelola
    fun updateLamaran(lamaranId: String, lamaran: LamaranModel) {
        _updatelamaranStatus.value = Resource.Loading()
        viewModelScope.launch {
            _updatelamaranStatus.value = repository.updateLamaran(lamaranId, lamaran)
        }
    }

    // Fungsi untuk menghapus Kelola
    fun deleteLamaran(lamaranId: String) {
        _deletelamaranStatus.value = Resource.Loading()
        viewModelScope.launch {
            _deletelamaranStatus.value = repository.deleteLamaran(lamaranId)
        }
    }



}