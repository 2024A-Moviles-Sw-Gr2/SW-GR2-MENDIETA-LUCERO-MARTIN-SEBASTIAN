package com.example.proyectoiib_kronos

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
import kotlin.math.absoluteValue

class AdapterDays (
    private val lista_fecha: ArrayList<Int>,
    private val lista_dias: ArrayList<String>
): RecyclerView.Adapter<AdapterDays.MyViewHolder>(){

    inner class MyViewHolder(
        view: View
    ): RecyclerView.ViewHolder(view){
        val day : TextView = view.findViewById(R.id.text_day)
        val number: TextView = view.findViewById(R.id.text_number)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.days_view, parent, false)

        return MyViewHolder(itemView)
    }
    override fun getItemCount(): Int {
        return this.lista_dias.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.number.text = this.lista_fecha[position].toString()
        holder.day.text = this.lista_dias[position]

        val currentDate = LocalDate.now()
        val formatted = currentDate.format(
            DateTimeFormatter.ofPattern("dd")
        )


        val dayOfWeek = currentDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES"))

        if (this.lista_fecha[position] == formatted.toInt()){

            holder.number.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.orange_low))
            holder.day.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.orange_low))

            holder.number.setTypeface(null, Typeface.BOLD)
            holder.day.setTypeface(null, Typeface.BOLD)
        }
    }
}