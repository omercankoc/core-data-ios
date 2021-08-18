package com.omercankoc.contentprovidersql

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

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

    }

    fun save(view : View){

    }

    fun update(view : View){

    }

    fun delete(view : View){
        
    }
}