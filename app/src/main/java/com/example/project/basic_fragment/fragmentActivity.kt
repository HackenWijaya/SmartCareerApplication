package com.example.project.basic_fragment

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.project.R

class fragmentActivity : AppCompatActivity() {
    private lateinit var btn_fragment1: Button
    private lateinit var btn_fragment2: Button
    private lateinit var btn_fragment3: Button
    private lateinit var toolbar: Toolbar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fragment)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        toolbar=findViewById(R.id.toolbar)
        toolbar.setTitle("Basic Fragment")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,FirstFragment())
            .commit()
        btn_fragment1 = findViewById(R.id.btn_fragment1)
        btn_fragment2 = findViewById(R.id.btn_fragment2)
        btn_fragment3 = findViewById(R.id.btn_fragment3)

        btn_fragment1.setOnClickListener {
            replaceFragment(FirstFragment())
        }
        btn_fragment2.setOnClickListener {
            replaceFragment(SecondFragment())
        }
        btn_fragment3.setOnClickListener {
            replaceFragment(ThirdFragment())
        }


    }

    fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}