package com.capgemini.reminderapp

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_view.*



class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        wrapper = DBWrapper(this)
        c = wrapper.getAllReminder()
        customAdapter = RReminderAdapter(this,c)
        reminderList.adapter = customAdapter
        //adapter = ReminderAdapter(this,R.layout.activity_reminder, reminders)
        //reminderList.adapter = adapter

    }

    fun addrem(view: View) {
        val i = Intent(this,AddActivity::class.java)
        startActivity(i)
    }

    fun backKey(view: View) {
        val i = Intent(this,MainActivity::class.java)
        startActivity(i)
    }
}