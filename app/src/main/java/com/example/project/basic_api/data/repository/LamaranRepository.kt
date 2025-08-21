package com.example.project.basic_api.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.project.basic_api.data.model.LamaranModel
import com.example.project.basic_api.utils.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LamaranRepository {
    private val firestore = FirebaseFirestore.getInstance()

    private val db = FirebaseFirestore.getInstance()
    val lamaranListLiveData = MutableLiveData<List<LamaranModel>>()
    val errorLiveData = MutableLiveData<String>()


    suspend fun getLamaran(): Resource<List<LamaranModel>> {
        return try {
            val LamaranList = mutableListOf<LamaranModel>()
            val snapshot = db.collection("lamaran").get().await()
            for (document in snapshot.documents) {
                val lamaran = document.toObject(LamaranModel::class.java)?.apply {
                    id = document.id
                }
                lamaran?.let { LamaranList.add(it) }
            }
            Resource.Success(LamaranList)
        } catch (e: Exception) {
            Log.e("LamaranRepository", "Error fetching data from Firebase: ${e.message}")
            Resource.Error("Error fetching data from Firebase: ${e.message}")
        }
    }
    fun fetchLamaranData() {
        firestore.collection("lamaran")
            .get()
            .addOnSuccessListener { result ->
                val lamaranList = result.documents.mapNotNull { document ->
                    val lamaran = document.toObject(LamaranModel::class.java)
                    lamaran?.copy(id = document.id) // Set ID dokumen Firebase
                }
                lamaranListLiveData.value = lamaranList
            }
            .addOnFailureListener { exception ->
                errorLiveData.value = exception.message
            }
    }

    suspend fun addLamaran(lamaran: LamaranModel): Resource<Unit> {
        return try {
            db.collection("lamaran").add(lamaran).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Error adding lamaran to Firebase: ${e.message}")
        }
    }

    suspend fun updateLamaran(lamaranId: String, lamaran: LamaranModel): Resource<Unit> {
        return try {
            db.collection("lamaran").document(lamaranId).set(lamaran).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Error updating lamaran: ${e.message}")
        }
    }

    suspend fun deleteLamaran(lamaranId: String): Resource<Unit> {
        return try {
            db.collection("lamaran").document(lamaranId).delete().await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Error deleting lamaran: ${e.message}")
        }
    }
    suspend fun getStatusByUid(uid: String): Resource<String> {
        return try {
            val snapshot = db.collection("lamaran")
                .whereEqualTo("uid", uid)
                .get()
                .await()

            if (!snapshot.isEmpty) {
                val status = snapshot.documents[0].getString("status") ?: "Belum Ditentukan"
                Resource.Success(status)
            } else {
                Resource.Error("Tidak Ada Data")
            }
        } catch (e: Exception) {
            Resource.Error("Gagal Mengambil Data: ${e.message}")
        }
    }

    suspend fun getLamaranByUserId(userId: String): Resource<List<LamaranModel>> {
        return try {
            val lamaranList = mutableListOf<LamaranModel>()
            val snapshot = db.collection("lamaran")
                .whereEqualTo("uid", userId)
                .get()
                .await()
            for (document in snapshot.documents) {
                val lamaran = document.toObject(LamaranModel::class.java)?.apply {
                    id = document.id
                }
                lamaran?.let { lamaranList.add(it) }
            }
            if (lamaranList.isEmpty()) {
                Resource.Empty("No lamaran found for this user.")
            } else {
                Resource.Success(lamaranList)
            }
        } catch (e: Exception) {
            Resource.Error("Error fetching lamaran by userId: ${e.message}")
        }
    }

}