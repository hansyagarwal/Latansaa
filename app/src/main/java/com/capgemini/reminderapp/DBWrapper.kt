package com.capgemini.reminderapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.widget.Toast

class DBWrapper(val context: Context) {
    var helper = DBHelper(context)
    var db: SQLiteDatabase = helper.writableDatabase

    fun saveReminders(title:String,desc:String,date:String,time:String) : Long{
        val cValues = ContentValues()
        cValues.put(DBHelper.CLM_TITLE,title)
        cValues.put(DBHelper.CLM_DESC,desc)
        cValues.put(DBHelper.CLM_DATE,date)
        cValues.put(DBHelper.CLM_TIME,time)

        return db.insert(DBHelper.TABLE_NAME, null, cValues)
    }

    fun getReminder(): Cursor {
        val clms = arrayOf(DBHelper.CLM_TITLE,DBHelper.CLM_DESC,DBHelper.CLM_DATE,DBHelper.CLM_TIME)
        return db.query(DBHelper.TABLE_NAME,clms,null,null,null,null,null)
    }
    fun getAllReminder() : Cursor {
        val query = "SELECT * FROM ${DBHelper.TABLE_NAME}"
        db = helper.readableDatabase
        var cursor = db.rawQuery(query, null)
        return cursor
    }

    fun updateReminders(remID: String,title: String,desc: String,date: String,time: String): Int {

        val cValues = ContentValues()
        cValues.put(DBHelper.CLM_TITLE,title)
        cValues.put(DBHelper.CLM_DESC,desc)
        cValues.put(DBHelper.CLM_DATE,date)
        cValues.put(DBHelper.CLM_TIME,time)

        //Toast.makeText(context,"ID: $remID",Toast.LENGTH_LONG).show()
        var convertID = remID.toInt()
        return db.update(DBHelper.TABLE_NAME,cValues," _id = $convertID", null)
    }


}