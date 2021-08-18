package com.omercankoc.contentprovidersql

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.database.sqlite.SQLiteOpenHelper as SQLiteOpenHelper

class ContentProviderSql : ContentProvider() {

    // SQLite veri tabanini olustur.
    private lateinit var sqLiteDatabase : SQLiteDatabase

    // Veritabanı oluşturma ve sürüm yönetimini yönetmek için bir yardımcı sınıf.
    class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION, null) {

        // Helper cagirildiginda tablo olustur.
        override fun onCreate(db : SQLiteDatabase) {
            db.execSQL(CREATE_TABLE)
        }

        // Guncelleme oldugunda versiyon degistir ve tabloyu tekrar olustur.
        override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE)
            onCreate(db)
        }
    }

    // ContentProvider olusturuldugunda DatabaseHelper ile ContentProvider'i bagla.
    override fun onCreate(): Boolean {
        val context = context
        val databaseHelper = DatabaseHelper(context)
        sqLiteDatabase = databaseHelper.writableDatabase
        return sqLiteDatabase != null
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object {
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "Languages"
        val DATABASE_TABLE = "languages"
        val CREATE_TABLE = "CREATE TABLE " + DATABASE_NAME + " (language TEXT NOT NULL image BLOB NOT NULL)"
        val COLUMN_PRODUCTNAME = "productname"
        val COLUMN_QUANTITY = "quantity"
    }
}