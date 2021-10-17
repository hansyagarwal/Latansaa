package com.capgemini.reminderapp

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class DBHelper(context: Context) : SQLiteOpenHelper(context, "listreminder",null,1) {
    companion object{
        val TABLE_NAME = "REMINDER"
        val CLM_ID = "_id"
        val CLM_TITLE = "title"
        val CLM_DESC = "description"
        val CLM_DATE = "date"
        val CLM_TIME = "time"

        val TABLE_QUERY = "create table $TABLE_NAME ($CLM_ID INTEGER PRIMARY KEY AUTOINCREMENT, $CLM_TITLE text, $CLM_DESC text, $CLM_DATE text, $CLM_TIME text)"
        //val ADD_ID = "ALTER TABLE $TABLE_NAME ADD _id int PRIMARY KEY AUTOINCREMENT"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try{
            db?.execSQL(TABLE_QUERY)
        }catch (e:Exception) {
            Log.d("DBHelper","ERROR in creating table: ${e.message}")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}