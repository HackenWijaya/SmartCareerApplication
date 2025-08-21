package com.example.project

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project.basic_api.data.firebase.FirebaseAuthService
import com.example.project.basic_api.data.repository.FirebaseRepository
import com.example.project.basic_api.ui.view.main.MainActivity
import com.example.project.basic_api.ui.view.main.admin.AdminHomeFragment
import com.example.project.basic_api.ui.view.main.admin.MainActivityAdmin
import com.example.project.basic_api.ui.viewmodel.FirebaseViewModel
import com.example.project.basic_api.utils.Resource
import com.example.project.basic_api.utils.ViewModelFactory
import com.example.project.databinding.ActivityLoginBinding

import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharPref: SharedPreferences

    private val firebaseViewModel: FirebaseViewModel by viewModels {
        ViewModelFactory(FirebaseViewModel::class.java) {
            val repository = FirebaseRepository(FirebaseAuthService())
            FirebaseViewModel(repository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharPref = getSharedPreferences("userPrefs", Context.MODE_PRIVATE)

        firebaseAuth = FirebaseAuth.getInstance()
        checkIfLoggedIn()
        observeLoginState()
        setupListeners()
    }

    private fun checkIfLoggedIn() {
        val isLoggedIn = firebaseAuth.currentUser != null
        if (isLoggedIn) {
            val userEmail = firebaseAuth.currentUser?.email
            navigateToAppropriateActivity(userEmail)
        }
    }

    private fun observeLoginState() {
        firebaseViewModel.loginState.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.d("Firebase User Authentication", "Mengirim Username Password...")
                }
                is Resource.Success -> {
                    val user = resource.data
                    Log.d("Firebase User Authentication", "Halo ${user?.email}")
                    navigateToAppropriateActivity(user?.email)
                }
                is Resource.Error -> {
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }

    private fun setupListeners() {
        binding.buttonregis?.setOnClickListener {
            navigateToRegisterActivity()
        }

        binding.buttonlogin.setOnClickListener {
            val email = binding.username.text.toString().trim()
            val password = binding.pass.text.toString().trim()

            val editor = sharPref.edit()
            editor.putString("username", email)
            editor.putString("password", password)
//
            editor.apply()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this,
                    "Username dan Password tidak boleh kosong",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            firebaseViewModel.login(this, email, password)
        }
    }

    private fun navigateToAppropriateActivity(email: String?) {
        when (email) {
            "admin@gmail.com" -> {
                val intent = Intent(this, MainActivityAdmin::class.java)
                startActivity(intent)
            }
            else -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        finish()
    }

    private fun navigateToRegisterActivity() {
        val intent = Intent(this, Registrasi::class.java)
        startActivity(intent)
    }
}
