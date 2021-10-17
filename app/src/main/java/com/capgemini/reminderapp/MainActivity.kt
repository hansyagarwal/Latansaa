package com.capgemini.reminderapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val r = ReminderReceiver()
        registerReceiver(r, IntentFilter(r.MY_BROADCAST_ACTION))
    }

    fun addReminder(view: View) {
        val i = Intent(this,AddActivity::class.java)
        startActivity(i)
    }
    fun viewReminder(view: View) {
        val i = Intent(this,ViewActivity::class.java)
        startActivity(i)
    }


    val MENU_CONTACT = 1
    val MENU_SETTINGS = 2
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menu?.add(0,MENU_CONTACT,1,"Contact Us")
        menu?.add(0,MENU_SETTINGS,1,"Settings")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId)
        {
            MENU_CONTACT->{
                val i = Intent(this,ContactActivity::class.java)
                startActivity(i)
            }
            MENU_SETTINGS->{
                val i = Intent(this,SettingsActivity::class.java)
                startActivity(i)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}