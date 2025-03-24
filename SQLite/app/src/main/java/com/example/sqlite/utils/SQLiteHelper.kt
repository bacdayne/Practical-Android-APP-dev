package com.example.sqlite.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase

class SQLiteHelper: SQLiteOpenHelper {
    constructor(context: Context) : super(context, DATABASE_NAME, null, DATABASE_VERSION)

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        private val DATABASE_NAME = "PhoneBook.db"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "contacts"
        val COL_ID = "id"
        val COL_NAME = "name"
        val COL_PHONE = "phone"
    }

    //Tao bang contacts
    private val SQL_CREATE_ENTRIES =
        "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME TEXT, $COL_PHONE TEXT)"

    //Xoa bang contacts
    private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"

    //Them du lieu
    fun insertContact(name: String, phone: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_PHONE, phone)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    //Sua du lieu
    fun updateContact(id: Int, name: String, phone: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_PHONE, phone)
        db.update(TABLE_NAME, values, "$COL_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    //Xoa du lieu
    fun deleteContact(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun getAllContacts(): List<Map<String, String>> {
        val db = readableDatabase
        val projection = arrayOf(COL_ID, COL_NAME, COL_PHONE)
        val cursor = db.query(TABLE_NAME, projection, null, null, null, null, null)
        val contacts = mutableListOf<Map<String, String>>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COL_ID))
                val name = getString(getColumnIndexOrThrow(COL_NAME))
                val phone = getString(getColumnIndexOrThrow(COL_PHONE))
                contacts.add(mapOf("id" to id.toString(), "name" to name, "phone" to phone))
            }
        }
        return contacts

    }



}
