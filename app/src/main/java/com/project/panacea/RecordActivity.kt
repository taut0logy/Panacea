package com.project.panacea

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.ArrayAdapter
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RecordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_record)

        val systolicPressure = HeartRateData.getSystolicPressure()
        val diastolicPressure = HeartRateData.getDiastolicPressure()

        var addNewBtn = findViewById<Button>(R.id.add_new);
        var statBtn = findViewById<Button>(R.id.stats);
        addNewBtn.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.addnew_dialog)
            val spinner: Spinner = dialog.findViewById(R.id.spinner)
            val items = arrayOf("AM", "PM")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
            spinner.adapter = adapter
            dialog.findViewById<Button>(R.id.Cancel).setOnClickListener {
                dialog.dismiss()
            }

            dialog.findViewById<Button>(R.id.Add).setOnClickListener {

            }


        }
    }
}