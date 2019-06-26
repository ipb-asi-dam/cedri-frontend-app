package com.example.cedri_app.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, TABLE_NAME, null, 1) {

    /**
     * Returns all the data from database
     * @return
     */
    val data: Cursor
        get() {
            val db = this.writableDatabase
            val query = "SELECT * FROM $TABLE_NAME"
            return db.rawQuery(query, null)
        }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL1 INTEGER PRIMARY KEY AUTOINCREMENT, $COL2 TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, i: Int, i1: Int) {
        //db.execSQL("DROP IF TABLE EXISTS $TABLE_NAME")
        //onCreate(db)
    }

    fun insertToken(token: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL2, token)

        Log.d(TAG, "addData: Adding $token to $TABLE_NAME")

        val result = db.insert(TABLE_NAME, null, contentValues)

        //if date as inserted incorrectly it will return -1
        if (-1.toLong() == result) {
            return false
        } else {
            return true
        }
    }

    /**
     * Returns only the token that matches the id passed in
     * @param id
     * @return
     */
    fun getAll(): Cursor {
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        return db.rawQuery(query, null)
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */
    fun getTokenFromDatabase(): Cursor {
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME LIMIT 1"
        return db.rawQuery(query, null)
    }

    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */
    fun updateToken(newToken: String, id: Int) {
        val db = this.writableDatabase
        val query = "UPDATE $TABLE_NAME SET $COL1 = newToken WHERE $COL1 = id"
        Log.d(TAG, "updateName: query: $query")
        Log.d(TAG, "updateName: Setting name to $newToken")
        db.execSQL(query)
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    fun deleteToken(id: Int) {
        val db = this.writableDatabase
        val query = ("DELETE FROM $TABLE_NAME WHERE $COL1 = id")
        Log.d(TAG, "deleteName: query: $query")
        db.execSQL(query)
    }

    companion object {

        private val TAG = "DatabaseHelper"
        private val TABLE_NAME = "token_table"
        private val COL1 = "ID"
        private val COL2 = "token"
    }

}