package com.capgemini.reminderapp

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity


class ReminderReceiver : BroadcastReceiver() {

    val MY_BROADCAST_ACTION = "com.capgemini.reminderapp.action.rem"
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        //val bundle = Bundle()
        val action = intent.action
        when(action){
            else -> {
                Toast.makeText(context,"Received", Toast.LENGTH_LONG).show()
                var b: Bundle? = intent.extras

                //val titleN = bundle.getString("title")
                //val descN = bundle.getString("desc")
                val titleN = b?.getString("title")
                val descN = b?.getString("desc")
                Toast.makeText(context,"Title: $titleN,  Desc: $descN",Toast.LENGTH_LONG).show()
                if (titleN != null && descN != null) if (descN != null) sendNotification(context,titleN,descN)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun scheduleEvent(context: Context,title:String, desc:String, date:String, time: String, mi: Int){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //val i = Intent(ReminderReceiver().MY_BROADCAST_ACTION)
        val bIntent = Intent(context,ReminderReceiver::class.java)
        bIntent.putExtra("title",title)
        bIntent.putExtra("desc",desc)
        val pi = PendingIntent.getBroadcast(context,1,bIntent,0)
        //alarmManager.setExact(AlarmManager.RTC,System.currentTimeMillis() + 1500,pi)
        var mil = AddActivity().convertDateTime(date,time,mi)
        alarmManager.setExact(AlarmManager.RTC,mil+mi,pi)
        //bundle.putString("title",title)
        //bundle.putString("desc",desc)
    }

    fun sendNotification(context:Context, titleN:String ="", descN:String = "") { //function to send notification
        val nManager =context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager //getting reference for notiication

        lateinit var builder: Notification.Builder //creating notification and channel for notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(
                    "test",
                    "Reminder App",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            nManager.createNotificationChannel(channel)
            builder =Notification.Builder(context, "test")
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)

        if(titleN.isEmpty() && descN.isEmpty())
            builder.setContentTitle("Reminder Added")
        else{
            builder.setContentTitle(titleN)
            builder.setContentText(descN)
        }

        val tryIntent = Intent(context,ViewActivity::class.java)
        val resumeIntent = PendingIntent.getActivity(context,0,tryIntent,0)
        builder.setContentIntent(resumeIntent)
        builder.setAutoCancel(true)
        val myNotification = builder.build()

        nManager.notify(1, myNotification) //sending notification
    }
}