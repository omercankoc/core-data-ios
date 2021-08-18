package com.omercankoc.contentprovidersql

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var listView : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)
    }

    // Menu ile View (XML) birbirine bagla.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.app_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Menu'de Item'e tiklandiginda ilgili yonlendirmeyi etiket yolla ve yap.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.add_item){
            val intent = Intent(applicationContext,DetailActivity::class.java)
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}