package com.example.proyectoiib_kronos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CategorySpinnerAdapter(
    context: Context?,
    private val items: List<String>
) : ArrayAdapter<String>(context!!, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)
        val item = getItem(position)

        val text = view.findViewById<TextView>(R.id.text_category)

        item?.let {
            text.text = it
        }

        return view
    }
}
