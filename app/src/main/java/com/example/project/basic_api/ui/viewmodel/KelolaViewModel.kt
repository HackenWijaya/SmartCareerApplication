package com.example.project.basic_api.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.project.basic_api.data.model.KelolaModel
import com.example.project.basic_api.data.repository.KelolaRepository
import com.example.project.basic_api.utils.Resource
import kotlinx.coroutines.launch

class KelolaViewModel(private val repository: KelolaRepository) : ViewModel() {

    private val _kelolaData = MutableLiveData<Resource<List<KelolaModel>>>()
    val kelolaData: LiveData<Resource<List<KelolaModel>>> = _kelolaData

    private val _addKelolaStatus = MutableLiveData<Resource<Unit>>()
    val addKelolaStatus: LiveData<Resource<Unit>> = _addKelolaStatus

    private val _updateKelolaStatus = MutableLiveData<Resource<Unit>>()
    val updateKelolaStatus: LiveData<Resource<Unit>> = _updateKelolaStatus

    private val _deleteKelolaStatus = MutableLiveData<Resource<Unit>>()
    val deleteKelolaStatus: LiveData<Resource<Unit>> = _deleteKelolaStatus

    // Fungsi untuk mengambil data Kelola
    fun getKelola() {
        _kelolaData.value = Resource.Loading()
        viewModelScope.launch {
            _kelolaData.value = repository.getKelola()
        }
    }

    // Fungsi untuk menambahkan Kelola baru
    fun addKelola(kelola: KelolaModel) {
        _addKelolaStatus.value = Resource.Loading()
        viewModelScope.launch {
            _addKelolaStatus.value = repository.addKelola(kelola)
        }
    }

    // Fungsi untuk update data Kelola
    fun updateKelola(kelolaId: String, kelola: KelolaModel) {
        _updateKelolaStatus.value = Resource.Loading()
        viewModelScope.launch {
            _updateKelolaStatus.value = repository.updateKelola(kelolaId, kelola)
        }
    }

    // Fungsi untuk menghapus Kelola
    fun deleteKelola(kelolaId: String) {
        _deleteKelolaStatus.value = Resource.Loading()
        viewModelScope.launch {
            _deleteKelolaStatus.value = repository.deleteKelola(kelolaId)
        }
    }
}