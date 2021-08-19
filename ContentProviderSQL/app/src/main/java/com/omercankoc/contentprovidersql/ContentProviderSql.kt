package com.omercankoc.contentprovidersql

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import com.omercankoc.contentprovidersql.ContentProviderSql.Companion.PROVIDER_NAME
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

        val sqliteQueryBuilder = SQLiteQueryBuilder()
        sqliteQueryBuilder.tables = DATABASE_TABLE

        when(URI_MATCHER.match(p0)){
            LANGUAGES -> sqliteQueryBuilder.projectionMap = LANGUAGES_MAP
            else -> {
                println("URI MATCHER ERROR! CPS54")
            }
        }

        var cursor = sqliteQueryBuilder.query(sqLiteDatabase,p1,p2,p3,null,null,p4)
        cursor.setNotificationUri(context!!.contentResolver,p0)
        return cursor
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        var rowID : Long = sqLiteDatabase.insert(DATABASE_TABLE,"",p1)
        if(rowID > 0){
            var uri = ContentUris.withAppendedId(CONTENT_URI,rowID)
            context!!.contentResolver.notifyChange(uri,null)
            return uri
        }
        throw SQLException("ERROR INSERT!")
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

        var NAME : String = "name"
        var IMAGE : String = "image"

        val PROVIDER_NAME = "com.omercankoc.contentprovidersql.ContentProviderSql"
        val CONTENT_URL = "content://" + PROVIDER_NAME + "/languages"
        val CONTENT_URI = Uri.parse(CONTENT_URL)

        var LANGUAGES : Int = 1
        var LANGUAGES_MAP = HashMap<String,String>()

        val URI_MATCHER = UriMatcher(UriMatcher.NO_MATCH)
        init {
            URI_MATCHER.addURI(PROVIDER_NAME,"languages", LANGUAGES)
        }
    }
}