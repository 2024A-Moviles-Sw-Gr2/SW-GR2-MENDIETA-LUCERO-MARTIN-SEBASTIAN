package com.example.proyectoiib_kronos

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker.
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // Create a new instance of DatePickerDialog and return it.
        return DatePickerDialog(requireContext(), this, year, month, day)

    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        val monthCorrected = month + 1


        val dayFormatted = String.format("%02d", day)
        val monthFormatted = String.format("%02d", monthCorrected)

        val dateFormatted = "$dayFormatted/$monthFormatted/$year"

        this.activity?.findViewById<EditText>(R.id.input_event_date)?.setText(dateFormatted)
    }
}