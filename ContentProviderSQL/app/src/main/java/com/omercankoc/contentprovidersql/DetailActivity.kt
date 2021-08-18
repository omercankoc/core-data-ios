package com.omercankoc.contentprovidersql

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlin.Exception

class DetailActivity : AppCompatActivity() {

    private lateinit var imageViewLanguage : ImageView
    private lateinit var editTextLanguage : EditText
    private lateinit var buttonSave : Button
    private lateinit var buttonUpdate : Button
    private lateinit var buttonDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        imageViewLanguage = findViewById(R.id.imageViewLanguage)
        editTextLanguage = findViewById(R.id.editTextLanguage)
        buttonSave = findViewById(R.id.buttonSave)
        buttonUpdate = findViewById(R.id.buttonUpdate)
        buttonDelete = findViewById(R.id.buttonDelete)
    }

    fun select(view : View){
        // Galeriye erismek icin izin yok ise izin iste.
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }
        // Galeriye erismek icin izin var ise galeriye yonlendir.
        else {
            val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,1)
        }
    }

    // Izin verildikten sonra galeriye yonlendir.
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == 1){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // Kullanicinin galeriden sectigi image'i ilgili nesneye aktar.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            val uri = data.data
            try{
                val image = MediaStore.Images.Media.getBitmap(this.contentResolver,uri)
                imageViewLanguage.setImageBitmap(image)
            } catch (e : Exception){
                e.printStackTrace()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun save(view : View){

    }

    fun update(view : View){

    }

    fun delete(view : View){

    }
}