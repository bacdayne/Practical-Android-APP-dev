package com.example.sqlitedemo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "PhoneBook.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "contacts"
        const val COL_NAME = "name"
        const val COL_PHONE = "phone"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME ($COL_NAME TEXT, $COL_PHONE TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    // Thêm dữ liệu
    fun insertContact(name: String, phone: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, name)
            put(COL_PHONE, phone)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Sửa dữ liệu
    fun updateContact(name: String, phone: String, oldName: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COL_NAME, name)
            put(COL_PHONE, phone)
        }
        db.update(TABLE_NAME, values, "$COL_NAME = ?", arrayOf(oldName))
        db.close()
    }

    // Xóa dữ liệu
    fun deleteContact(name: String) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COL_NAME = ?", arrayOf(name))
        db.close()
    }

    // Lấy dữ liệu
    @SuppressLint("Range")
    fun getAllContacts(): ArrayList<HashMap<String, String>> {
        val contacts = ArrayList<HashMap<String, String>>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        with(cursor) {
            while (moveToNext()) {
                val contact = HashMap<String, String>()
                contact[COL_NAME] = getString(getColumnIndexOrThrow(COL_NAME))
                contact[COL_PHONE] = getString(getColumnIndexOrThrow(COL_PHONE))
                contacts.add(contact)
            }
            cursor.close()
        }
        db.close()
        return contacts
    }
}
