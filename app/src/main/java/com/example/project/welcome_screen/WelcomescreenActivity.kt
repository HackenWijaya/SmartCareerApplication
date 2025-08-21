package com.example.project.welcome_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.project.LoginActivity
import com.example.project.R
import com.example.project.basic_api.ui.view.main.MainActivity
import com.example.project.basic_api.ui.view.main.admin.MainActivityAdmin
import com.google.firebase.auth.FirebaseAuth
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class WelcomescreenActivity : AppCompatActivity() {
    private lateinit var viewpager: ViewPager2
    private lateinit var dotindicator: DotsIndicator
    private lateinit var btnnext: Button
    private lateinit var btnskip: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcomescreen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewpager = findViewById(R.id.viewpager)
        dotindicator = findViewById(R.id.dotid)
        btnnext = findViewById(R.id.btnnext)
        btnskip = findViewById(R.id.skip)

        val fragmentlist = listOf(welcome1_fragment(), welcome2_fragment(), welcome3_fragment())
        val adapter = pageradapter(this, fragmentlist)
        viewpager.adapter = adapter

        dotindicator.attachTo(viewpager)
        btnnext.setOnClickListener {
            if (viewpager.currentItem < fragmentlist.size - 1){
                viewpager.currentItem += 1
            }else{
                finishwelcomescreen()
            }
        }
        btnskip.setOnClickListener {
            finishwelcomescreen()
        }
        firebaseAuth = FirebaseAuth.getInstance()

        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == fragmentlist.size - 1){
                    btnnext.text = "Finish"
                    btnskip.visibility = View.GONE

                }else {
                    btnnext.text = "Next"
                    btnskip.visibility=View.VISIBLE
                }

            }
        })
    }

    private fun finishwelcomescreen(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser?.email == "admin@gmail.com"){
            val intent = Intent(this, MainActivityAdmin::class.java)
            startActivity(intent)
        }else if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}