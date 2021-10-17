package com.capgemini.reminderapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class Dialog: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        lateinit var dlg:Dialog
        val parent = activity!!
        val builder = AlertDialog.Builder(parent)

        val bundle = arguments
        val dlgType = bundle?.getInt("type")
        when(dlgType){
            1->{
                dlg = TimePickerDialog(parent, parent as TimePickerDialog.OnTimeSetListener,
                        12, 0,true)
            }
            2->{
                dlg = DatePickerDialog(parent,parent as DatePickerDialog.OnDateSetListener,
                        2021, 3, 25)
            }
        }


        /*dlg = builder.create()
        dlg.setCancelable(false)*/
        return dlg
    }
}