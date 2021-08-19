package com.omercankoc.contentprovidersql

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private lateinit var listView : ListView

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listView)

        var languageList = ArrayList<String>()
        var imageList = ArrayList<Bitmap>()

        // Gorsel nesne ile veri setini birbirine bagla.
        val languageAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,languageList)
        listView.adapter = languageAdapter

        var url = "content://com.omercankoc.contentprovidersql.ContentProviderSql"
        var uri = Uri.parse(url)

        // Verileri cek.
        val cursor = contentResolver.query(uri,null,null,null,ContentProviderSql.NAME)
        if(cursor != null){
            while (cursor.moveToNext()){
                languageList.add(cursor.getString(cursor.getColumnIndex(ContentProviderSql.NAME)))
                var bytes = cursor.getBlob(cursor.getColumnIndex(ContentProviderSql.IMAGE))
                var image = BitmapFactory.decodeByteArray(bytes,0,bytes.size)
                imageList.add(image)
                languageAdapter.notifyDataSetChanged()
            }
        }
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