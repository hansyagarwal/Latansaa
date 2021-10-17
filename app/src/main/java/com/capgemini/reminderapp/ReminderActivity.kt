package com.capgemini.reminderapp

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReminderActivity:  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
    }

    fun deleteAct(view: View) {}
    fun editAct(view: View) {
        Toast.makeText(this,"This is edit activity",Toast.LENGTH_LONG).show()
    }


}