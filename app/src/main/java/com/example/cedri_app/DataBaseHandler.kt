package com.example.cedri_app

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    val listContents: Cursor
        get() {
            val db = this.writableDatabase
            return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL1 INTEGER PRIMARY KEY AUTOINCREMENT, $COL2 VARCHAR(256))"
        db!!.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun addData(token: DataInformation): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL2, token.token)

        val result = db.insert(TABLE_NAME, null, contentValues)

        db.close()

        //if date as inserted incorrectly it will return -1
        return if (result == -1.toLong()) {
            return false
        } else {
            true
        }
    }

    companion object {

        val DATABASE_NAME = "CedriDB.db"
        val TABLE_NAME = "data"
        val COL1 = "ID"
        val COL2 = "TOKEN"
    }
}