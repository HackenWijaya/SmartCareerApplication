package com.example.project.basic_listview

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project.R

class ListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_list_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listView: ListView = findViewById(R.id.ListView)

        val menuList = listOf(
            ListModel("menu 1", "deskripsi menu 1", R.drawable.ic_menu),
            ListModel("menu 2", "deskripsi menu 2", R.drawable.ic_home),
            ListModel("menu 3", "deskripsi menu 3", R.drawable.ic_person),
            ListModel("menu 4", "deskripsi menu 4", R.drawable.ic_profile),
            ListModel("menu 5", "deskripsi menu 5", R.drawable.ic_settings),

        )
        val adapter = ListAdapter(this, menuList)
        listView.adapter = adapter

        listView.setOnItemClickListener{ parent, view, position, id ->
            val selecteditem = menuList[position]
            if (selecteditem.name == "menu 5"){
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "kamu klik ${selecteditem.name}", Toast.LENGTH_LONG).show()
            }
        }
    }
}