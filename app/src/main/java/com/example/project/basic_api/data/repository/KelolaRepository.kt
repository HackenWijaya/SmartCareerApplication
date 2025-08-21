package com.example.project.basic_api.data.repository

import android.util.Log
import com.example.project.basic_api.data.model.KelolaModel
import com.example.project.basic_api.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class KelolaRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getKelola(): Resource<List<KelolaModel>> {
        return try {
            val kelolaList = mutableListOf<KelolaModel>()
            val snapshot = db.collection("kelola").get().await()
            for (document in snapshot.documents) {
                val kelola = document.toObject(KelolaModel::class.java)?.apply {
                    id = document.id
                }
                kelola?.let { kelolaList.add(it) }
            }
            Resource.Success(kelolaList)
        } catch (e: Exception) {
            Log.e("KelolaRepository", "Error fetching data from Firebase: ${e.message}")
            Resource.Error("Error fetching data from Firebase: ${e.message}")
        }
    }

    suspend fun addKelola(kelola: KelolaModel): Resource<Unit> {
        return try {
            db.collection("kelola").add(kelola).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Error adding kelola to Firebase: ${e.message}")
        }
    }

    suspend fun updateKelola(kelolaId: String, kelola: KelolaModel): Resource<Unit> {
        return try {
            db.collection("kelola").document(kelolaId).set(kelola).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Error updating kelola: ${e.message}")
        }
    }

    suspend fun deleteKelola(kelolaId: String): Resource<Unit> {
        return try {
            db.collection("kelola").document(kelolaId).delete().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Error deleting kelola: ${e.message}")
        }
    }
}