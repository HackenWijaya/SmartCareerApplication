package com.example.project.basic_api.ui.view.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.project.R
import com.example.project.basic_api.ui.view.main.home.HomeFragment
import com.example.project.basic_api.ui.view.main.lamaran.LamaranFragment
import com.example.project.basic_api.ui.view.main.message.MessageFragment
import com.example.project.basic_api.ui.view.main.more.MoreFragment
import com.example.project.basic_api.ui.view.main.panduancv.PanduanCVFragment
import com.example.project.basic_api.ui.view.main.profil.ProfilFragment
import com.example.project.basic_api.ui.view.main.rekomendasipekerjaan.RekomendasiFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }
        loadFragment(HomeFragment())

        val bottmNav : BottomNavigationView = findViewById(R.id.bottm_nav_view)
        bottmNav.setOnItemSelectedListener{
            when(it.itemId){
                R.id.home -> {
                    loadFragment(HomeFragment())
                }
                R.id.message -> {
                    loadFragment(RekomendasiFragment())
                }
                R.id.more -> {
                    loadFragment(ProfilFragment())
                }
            }
            true
        }

    }
    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container,fragment)
        transaction.commit()
    }

}