package com.example.project

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project.R.id.btnback
import com.example.project.basic_fragment.fragmentActivity
import com.example.project.basic_listview.ListViewActivity
import com.example.project.welcome_screen.WelcomescreenActivity

class secondactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_secondactivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnback: Button = findViewById(R.id.btnback)
        btnback.setOnClickListener {
            finish()
        }
        val btnweb: Button = findViewById(R.id.btnweb)
        val welcome : TextView = findViewById(R.id.textView8)
        val sharedpref = getSharedPreferences("userpref", Context.MODE_PRIVATE)
        val username = sharedpref.getString("username", null)
        welcome.text = username
        val btn_fragment: Button = findViewById(R.id.btn_fragment)
        val btnwelcome: Button = findViewById(R.id.btnwelcome)
        val btnlist: Button = findViewById(R.id.btnlist)
        val btnlistcard: Button = findViewById(R.id.btnlistcard)

        btn_fragment.setOnClickListener {
            val intent = Intent(this, fragmentActivity::class.java)
            startActivity(intent)
        }

        btnwelcome.setOnClickListener {
            val intent = Intent(this, WelcomescreenActivity::class.java)
            startActivity(intent)
        }
        btnlistcard.setOnClickListener {
//            val intent = Intent(this, RecycleviewActivity::class.java)
            startActivity(intent)
        }

        btnweb.setOnClickListener {
            val intent= Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }
        btnlist.setOnClickListener {
            val intent= Intent(this, ListViewActivity::class.java)
            startActivity(intent)
        }
    }
}