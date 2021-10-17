package com.capgemini.reminderapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast

val PREF_NAME = "credentials"

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    }


    fun radioClicked(view: View) {
        val pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        val editor = pref.edit()
        var milliseconds: Int? = 0
        if(view is RadioButton){
            val isChecked = view.isChecked
            if(isChecked){
                when(view.id){
                    R.id.fiveR->{
                        milliseconds = 5*60000
                        Toast.makeText(this,"5mins",Toast.LENGTH_LONG).show()
                    }
                    R.id.tenR->{
                        milliseconds = 10*60000
                        Toast.makeText(this,"10mins",Toast.LENGTH_LONG).show()
                    }
                    R.id.thirtyR->{
                        milliseconds = 30*60000
                        Toast.makeText(this,"30mins",Toast.LENGTH_LONG).show()
                    }
                    R.id.hourR->{
                        milliseconds = 60*60000
                        Toast.makeText(this,"1hour",Toast.LENGTH_LONG).show()
                    }
                }
                editor.putInt("milli", milliseconds!!)
                editor.commit()
            }
        }
    }
}