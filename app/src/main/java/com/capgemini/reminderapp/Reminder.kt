package com.capgemini.reminderapp

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_add.view.*
import kotlinx.android.synthetic.main.activity_reminder.view.*

lateinit var customAdapter : RReminderAdapter
lateinit var wrapper: DBWrapper
val reminders = mutableListOf<Reminder>()
lateinit var c:Cursor
//lateinit var adapter: ReminderAdapter

open class RReminderAdapter(context: Context, cursor: Cursor) : CursorAdapter(context,cursor){

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.activity_reminder,parent,false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val helper = context?.let { DBHelper(it) } //db and table available
        val db: SQLiteDatabase? = helper?.writableDatabase
        val wrapper = DBWrapper(context!!)

        val titleT =view?.findViewById<TextView>(R.id.titleT)
        val descT =view?.findViewById<TextView>(R.id.descT)
        val dateT =view?.findViewById<TextView>(R.id.dateT)
        val timeT =view?.findViewById<TextView>(R.id.timeT)
        val deleteB = view?.findViewById<ImageButton>(R.id.deleteB)
        val editB = view?.findViewById<ImageButton>(R.id.editB)

        titleT?.text = c?.getString(c.getColumnIndex(DBHelper.CLM_TITLE))
        descT?.text = c?.getString(c.getColumnIndex(DBHelper.CLM_DESC))
        dateT?.text = "Date: " + c?.getString(c.getColumnIndex(DBHelper.CLM_DATE))
        timeT?.text = "Time: " + c?.getString(c.getColumnIndex(DBHelper.CLM_TIME))

        val cu = c?.getString(c.getColumnIndex(DBHelper.CLM_ID))
        //avoid using click listener
        deleteB?.setOnClickListener(View.OnClickListener {
            db?.execSQL("DELETE FROM ${DBHelper.TABLE_NAME} WHERE _id = $cu")
            Toast.makeText(context, "Reminder Deleted: $cu", Toast.LENGTH_LONG).show()
            val cr = context.contentResolver
            var args = arrayOf(DBHelper.CLM_TITLE)
            cr.delete(CalendarContract.Events.CONTENT_URI,"${CalendarContract.Events.TITLE}=?",args)
            c = wrapper.getAllReminder()
            customAdapter.swapCursor(c)
        })

        val oldTitle = c?.getString((c.getColumnIndex((DBHelper.CLM_TITLE))))
        val oldDesc = c?.getString(c.getColumnIndex(DBHelper.CLM_DESC))
        val oldDate = c?.getString(c.getColumnIndex(DBHelper.CLM_DATE))
        val oldTime = c?.getString(c.getColumnIndex(DBHelper.CLM_TIME))

        editB?.setOnClickListener(View.OnClickListener {
            val i = Intent(context, AddActivity::class.java)
            i.putExtra("oldtitle",oldTitle)
            i.putExtra("olddesc",oldDesc)
            i.putExtra("olddate",oldDate)
            i.putExtra("oldtime",oldTime)
            i.putExtra("remID",cu)

            //Toast.makeText(context, "Title: $oldTitle, $oldDesc, $oldDate, $oldTime", Toast.LENGTH_LONG).show()
            context.startActivity(i)
        })
    }
}


open class Reminder(var title: String, var desc: String, var date: String, var time: String)
/*
open class ReminderAdapter(context: Context, val layout: Int, val data: List<Reminder>):
        ArrayAdapter<Reminder>(context, layout, data){

    override fun getItem(position: Int): Reminder? {
        return data[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val reminder = getItem(position)
        val view = convertView?: LayoutInflater.from(context).inflate(layout, null)

        val titleT =view.findViewById<TextView>(R.id.titleT)
        val descT =view.findViewById<TextView>(R.id.descT)
        val dateT =view.findViewById<TextView>(R.id.dateT)
        val timeT =view.findViewById<TextView>(R.id.timeT)
        val deleteB = view.findViewById<ImageButton>(R.id.deleteB)
        val editB = view.findViewById<ImageButton>(R.id.editB)

        titleT.text = reminder?.title
        descT.text = reminder?.desc
        dateT.text = reminder?.date
        timeT.text = reminder?.time
        */
/*titleT.text = cursor.getString(cursor.getColumnIndex(DBHelper.CLM_TITLE))
        descT.text = cursor.getString(cursor.getColumnIndex(DBHelper.CLM_DESC))
        dateT.text = cursor.getString(cursor.getColumnIndex(DBHelper.CLM_DATE))
        timeT.text = cursor.getString(cursor.getColumnIndex(DBHelper.CLM_DATE))*//*


        deleteB.setOnClickListener(View.OnClickListener {
            reminders.remove(getItem(position))
            Toast.makeText(context, "Reminder Deleted", Toast.LENGTH_LONG).show()
            adapter.notifyDataSetChanged()
        })
        editB.setOnClickListener(View.OnClickListener {
            val i = Intent(context, AddActivity::class.java)
            val b = Bundle()
            startActivity(context, i, b)
            //val newTitle: EditText = it.findViewById<EditText>(R.id.getTitle)
            //newTitle.setText(it.titleT.toString())
            //it.getTitle.text = Editable.Factory.getInstance().newEditable(reminders[position].title)
            //it.findViewById<EditText>(R.id.getTitle).setText(getItem(position)?.title)
            //reminders.remove(getItem(position))
        })

        return view
    }
}*/
