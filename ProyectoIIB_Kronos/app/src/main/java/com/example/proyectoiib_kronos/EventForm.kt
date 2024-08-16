package com.example.proyectoiib_kronos

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class EventForm : AppCompatActivity() {

    var categorySelected: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_event_form)

        val btnSaveEvent = findViewById<Button>(R.id.btn_save_category)
        val inputEventTitle = findViewById<TextInputEditText>(R.id.input_event_title)
        val inputEventDate = findViewById<TextInputEditText>(R.id.input_event_date)
        val inputEventHour = findViewById<TextInputEditText>(R.id.input_event_hour)
        val btnHourPicker = findViewById<ImageView>(R.id.btn_hour_picker)
        val btnDatePicker = findViewById<ImageView>(R.id.btn_date_picker)
        val spinner: Spinner = findViewById(R.id.spinner_category)

        btnHourPicker.setOnClickListener {
            TimePickerFragment().show(supportFragmentManager, "timePicker")
        }

        btnDatePicker.setOnClickListener {
            val newFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")
        }

        val categorias = Database.data!!.getAllCategories().map { it.name }
        val adapter = CategorySpinnerAdapter(this, categorias)
        spinner.adapter = adapter
        adapter.notifyDataSetChanged()
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                categorySelected = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No hacer nada
            }
        }

        btnSaveEvent.setOnClickListener {
            Database.data!!.createEvent(
                inputEventTitle.text.toString(),
                inputEventDate.text.toString(),
                inputEventHour.text.toString(),
                if (categorySelected != null) Database.data!!.findCategoryByName(categorySelected!!)!!.id else -1
            )
            this.goToActivity(MainActivity::class.java)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>
    ) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}