package com.capgemini.reminderapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
    }

    fun contactClick(view: View) {
        lateinit var intent: Intent
        when(view.id)
        {
            R.id.smsB -> {
                Toast.makeText(this,"Switching to SMS app...",Toast.LENGTH_LONG).show()
                intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:9636746750"))
            }
            R.id.callB -> {
                Toast.makeText(this,"Switching to Call app",Toast.LENGTH_LONG).show()
                intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:9636746550"))
            }
            R.id.mailB -> {
                Toast.makeText(this,"Mail",Toast.LENGTH_LONG).show()
                intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:reminder.helpdesk@gmail.com"))
            }
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}