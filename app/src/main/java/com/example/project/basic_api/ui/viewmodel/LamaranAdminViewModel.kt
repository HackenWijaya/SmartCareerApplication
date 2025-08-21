package com.example.project.basic_api.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project.basic_api.data.model.LamaranModel
import com.example.project.basic_api.data.repository.LamaranRepository

class LamaranAdminViewModel : ViewModel(){
    private val repository = LamaranRepository()

    // LiveData untuk daftar lamaran
    val lamaranListLiveData: LiveData<List<LamaranModel>> = repository.lamaranListLiveData
    val errorLiveData: LiveData<String> = repository.errorLiveData

    // LiveData untuk opsi filter (dari backend)
    private val _filterOptions = MutableLiveData<List<String>>()
    val filterOptions: LiveData<List<String>> get() = _filterOptions

    // LiveData untuk daftar lamaran yang difilter
    private val _filteredLamaranLiveData = MutableLiveData<List<LamaranModel>>()
    val filteredLamaranLiveData: LiveData<List<LamaranModel>> get() = _filteredLamaranLiveData

    fun loadLamaranData() {
        repository.fetchLamaranData()
    }

    fun loadFilterOptions() {
        val options = listOf("Semua", "Belum Diproses", "Diterima", "Ditolak")
        _filterOptions.value = options
    }

    fun filterLamaran(status: String) {
        val allLamarans = lamaranListLiveData.value ?: emptyList()
        _filteredLamaranLiveData.value = when (status) {
            "Semua" -> allLamarans
            "Belum Diproses" -> allLamarans.filter { it.status.isEmpty() || it.status == "Belum Diproses" }
            else -> allLamarans.filter { it.status == status }
        }
    }
}