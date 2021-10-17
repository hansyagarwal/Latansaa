package com.capgemini.reminderapp

import android.app.*
import android.content.*
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.TimeFormat
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_view.*
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*



class AddActivity() : AppCompatActivity(){
    val receiver = ReminderReceiver()

    val bundle = Bundle()
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        val filter = IntentFilter(ReminderReceiver().MY_BROADCAST_ACTION)
        registerReceiver(receiver,filter)

        var b: Bundle? = intent.extras
        val oldTitle = b?.getString("oldtitle")
        val oldDesc = b?.getString("olddesc")
        val oldDate = b?.getString("olddate")
        val oldTime = b?.getString("oldtime")
        val remID = b?.getString("remID")
        if(oldTitle!=null && oldDesc!=null && oldDate!=null && oldTime!=null)
        {
            Toast.makeText(this,"amaing tis wokring ${oldTitle}",Toast.LENGTH_LONG).show()
            getTitle.setText(oldTitle)
            getDesc.setText(oldDesc)
            getDate.setText(oldDate)
            getTime.setText(oldTime)

            var confirm = bundle.putInt("confirm",5)
            var remID = bundle.putString("remID",remID)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addInfo(view: View) {

        var title = getTitle.text.toString()
        var desc = getDesc.text.toString()
        var date = getDate.text.toString()
        var time = getTime.text.toString()
        var calendar = calendarB.isChecked
        var schedule = scheduleB.isChecked

        if(title.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty())
        {
            var mi = pref.getInt("milli",0)
            //Toast.makeText(this,"bundle working: $mi",Toast.LENGTH_LONG).show()
            val wrapper = DBWrapper(this)


            var update = bundle.getInt("confirm")
            var remID = bundle.getString("remID")
            if(update == 5) {
                if (remID != null) {
                    wrapper.updateReminders(remID,title,desc,date,time)
                }
            } else {
                wrapper.saveReminders(title,desc, date, time)
            }

            var str = "Title: $title \n Description: $desc \n Date: $date, Time: $time"
            Toast.makeText(this, "$str", Toast.LENGTH_SHORT).show()
            ReminderReceiver().sendNotification(this)

            //reminders.add(Reminder(title, desc, "Date: $date", "Time: $time"))
            var i =  Intent(this, ViewActivity::class.java)
            startActivity(i)

            //checking if add reminder to calendar is checked
            if(calendar) calendarEvent(title,desc,date,time,mi)
            //checking if shedule reminder is checked
            if(schedule) ReminderReceiver().scheduleEvent(this,title,desc,date,time,mi)

        } else Toast.makeText(this,"Title,date and time cannot be empty",Toast.LENGTH_LONG).show()
    }

    // converting date and time to milliseconds
    @RequiresApi(Build.VERSION_CODES.O)
    fun convertDateTime(date:String,time:String,mi: Int):Long{
        var formatter = DateTimeFormatter.ofPattern("d-M-yy")
        val dateTime = LocalDate.parse(date,formatter)
        val t = LocalTime.parse(time, DateTimeFormatter.ofPattern("H:m"))
        val m = t.atDate(dateTime)
        val a = "$m:00.800Z"
        val mil = Instant.parse(a).toEpochMilli() - 19800000 - mi
        return mil
        //val duration = Duration.between(Instant.parse(a),Instant.now())
    }

    /*@RequiresApi(Build.VERSION_CODES.O)
    fun scheduleEvent(title:String, desc:String, date:String, time: String, mi: Int){
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val i = Intent(ReminderReceiver().MY_BROADCAST_ACTION)
        val bIntent = Intent(this,ReminderReceiver::class.java)
        bIntent.putExtra("title",title)
        bIntent.putExtra("desc",desc)
        val pi = PendingIntent.getBroadcast(this,1,bIntent,0)
        //alarmManager.setExact(AlarmManager.RTC,System.currentTimeMillis() + 1500,pi)
        var mil = convertDateTime(date,time,mi)
        alarmManager.setExact(AlarmManager.RTC,mil+mi,pi)
        bundle.putString("title",title)
        bundle.putString("desc",desc)
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    fun calendarEvent(title:String, desc:String, date:String, time: String, mi: Int){
        var mil = convertDateTime(date,time,mi)

        val cValues = ContentValues()
        cValues.put(CalendarContract.Events.CALENDAR_ID,3)
        cValues.put(CalendarContract.Events.TITLE, title)
        cValues.put(CalendarContract.Events.DESCRIPTION,desc)
        cValues.put(CalendarContract.Events.DTSTART,mil+mi)
        cValues.put(CalendarContract.Events.DTEND,mil+(60*60000))
        cValues.put(CalendarContract.Events.ALL_DAY,0)
        cValues.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)

        this.contentResolver.insert(CalendarContract.Events.CONTENT_URI,cValues)

        /*val intent = Intent(Intent.ACTION_INSERT).apply {
            data = CalendarContract.Events.CONTENT_URI
            putExtra(CalendarContract.Events.TITLE, title)
            putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, mil)
        }
        if (intent.resolveActivity(packageManager) != null) {
            Toast.makeText(this, "added calendar", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }*/
    }

    fun cancelInfo(view: View) {
        finish()
    }

    //broadcast receiver to send notification when user chooses to schedule the reminder
    /*inner class ReminderReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            *//*when(intent?.action){
                MY_BROADCAST_ACTION -> {
                    Toast.makeText(context,"Reminder.........",Toast.LENGTH_LONG).show()
                    val titleN = bundle.getString("title")
                    val descN = bundle.getString("desc")
                    if (titleN != null && descN != null) if (descN != null) sendNotification(titleN,descN)
                }
            }*//*
        }
    }*/

    open /*fun sendNotification(titleN:String ="", descN:String = "") { //function to send notification
        val nManager =getSystemService(NOTIFICATION_SERVICE) as NotificationManager //getting reference for notiication

        lateinit var builder: Notification.Builder //creating notification and channel for notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            val channel = NotificationChannel(
                    "test",
                    "Reminder App",
                    NotificationManager.IMPORTANCE_DEFAULT
            )
            nManager.createNotificationChannel(channel)
            builder =Notification.Builder(this, "test")
        }
        builder.setSmallIcon(R.mipmap.ic_launcher)

        if(titleN.isEmpty() && descN.isEmpty())
            builder.setContentTitle("Reminder Added")
        else{
            builder.setContentTitle(titleN)
            builder.setContentText(descN)
        }

        val tryIntent = Intent(this,ViewActivity::class.java)
        val resumeIntent = PendingIntent.getActivity(this,0,tryIntent,0)
        builder.setContentIntent(resumeIntent)
        builder.setAutoCancel(true)
        val myNotification = builder.build()

        nManager.notify(1, myNotification) //sending notification
    }*/

    //function to make time picker
    fun timeP(view: View) {
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                getTime.setText(String.format("%d:%d", hourOfDay, minute))
            }
        }, hour, minute, true)

        mTimePicker.show()
    }

    //function to make date picker
    @RequiresApi(Build.VERSION_CODES.N)
    fun dateP(view: View):String {
        val datePicker: DatePickerDialog
        val currentDate = Calendar.getInstance()
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        val month = currentDate.get(Calendar.MONTH)
        val year = currentDate.get(Calendar.YEAR)
        var newDate: String = ""
        //Toast.makeText(this,"$month",Toast.LENGTH_LONG).show()
        datePicker = DatePickerDialog(this, object: DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                getDate.setText("$dayOfMonth-${month+1}-${year-2000}")
                newDate = "$dayOfMonth"
            }
        },year,month,day)
        datePicker.show()
        return newDate
    }
}

