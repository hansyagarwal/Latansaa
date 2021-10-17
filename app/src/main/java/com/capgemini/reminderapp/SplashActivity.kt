package com.capgemini.reminderapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val SPLASH_TIME_OUT: Long = 200

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //splash screen
        Handler().postDelayed({ /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this, MainActivity::class.java)
            this.startActivity(mainIntent)
            this.finish()
        }, 1500)
    }
}