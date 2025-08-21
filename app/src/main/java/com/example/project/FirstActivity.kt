package com.example.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_first)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val welcome : TextView = findViewById(R.id.textView)
        val btnalert: Button = findViewById(R.id.btnalert)
        val sharedpref = getSharedPreferences("userpref", Context.MODE_PRIVATE)
        val username = sharedpref.getString("usernamereg", null)
        val btnlogout : Button = findViewById(R.id.logout)




        btnlogout.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Ingin Logout Dari Sistem")
                .setMessage("Apakah anda yakin?")
                .setPositiveButton("iya"){ dialogInterface, wich ->
                    val editor = sharedpref.edit()
                    editor.clear()
                    editor.apply()
                    val intent = Intent(this,LoginActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()

                }
                .setNegativeButton("tidak", null)
                .show()

        }
        welcome.text = "Selamat Datang " + username

        val btnNext: Button = findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            println("masuk")
            val i = Intent(this, secondactivity::class.java)
            i.putExtra("username", "rusli" )
            startActivity(i)
        }

        btnalert.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Ingin Keluar Dari Aplikasi")
                .setMessage("Apakah anda yakin?")
                .setPositiveButton("iya"){ dialogInterface, wich ->
                    Toast.makeText(this, "oke deh", Toast.LENGTH_LONG).show()
                        finish()

                }
                .setNegativeButton("tidak", null)
                .show()
        }
    }
}