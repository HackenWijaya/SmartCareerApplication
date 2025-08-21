package com.example.project

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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
import com.example.project.basic_api.ui.viewmodel.FirebaseViewModel
import com.example.project.basic_api.utils.Resource
import com.example.project.basic_api.utils.ViewModelFactory
import com.example.project.databinding.ActivityRegistrasiBinding
import com.google.firebase.auth.FirebaseAuth

class Registrasi : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrasiBinding

    private val firebaseViewModel: FirebaseViewModel by viewModels {
        ViewModelFactory(FirebaseViewModel::class.java) {
            val repository = FirebaseRepository(FirebaseAuthService())
            FirebaseViewModel(repository)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
        observeRegisterState()
    }

    private fun setupListeners() {
        binding.buttonlogin.setOnClickListener {
            navigateToLogin()
        }

        binding.buttonregis.setOnClickListener {
            val email = binding.username.text.toString()
            val password = binding.pass.text.toString()
            val confirmPassword = binding.confpass.text.toString()

            if (validateInputs(email, password, confirmPassword)) {
                registerUser(email, password)
            }
        }
    }

    private fun validateInputs(
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        return when {
            email.isEmpty() -> {
                Toast.makeText(this, "Email harus diisi!", Toast.LENGTH_SHORT).show()
                false
            }
            password.isEmpty() -> {
                Toast.makeText(this, "Password harus diisi!", Toast.LENGTH_SHORT).show()
                false
            }
            confirmPassword.isEmpty() -> {
                Toast.makeText(this, "Konfirmasi Password harus diisi!", Toast.LENGTH_SHORT).show()
                false
            }
            password != confirmPassword -> {
                Toast.makeText(this, "Password tidak cocok!", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }

    private fun registerUser(email: String, password: String) {
        firebaseViewModel.register(this, email, password)
    }

    private fun observeRegisterState() {
        firebaseViewModel.registerState.observe(this) { state ->
            when (state) {
                is Resource.Loading -> {
                    Toast.makeText(this, "Sedang diproses!", Toast.LENGTH_SHORT).show()
                }
                is Resource.Success -> {
                    Toast.makeText(this, "Registrasi berhasil!", Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                }
                is Resource.Empty -> {
                }
            }
        }
    }

    private fun navigateToLogin() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
