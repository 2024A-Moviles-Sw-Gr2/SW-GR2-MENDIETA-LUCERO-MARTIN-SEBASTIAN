package com.example.a04_recreateapp_recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewProductCharacteristicAdapter(
    private val list: ArrayList<ProductCharacteristic>
): RecyclerView.Adapter<RecyclerViewProductCharacteristicAdapter.MyViewHolder>() {

    inner class MyViewHolder(
        view: View
    ): RecyclerView.ViewHolder(view) {
        val productCharacNameTV: TextView
        val productCharacValueTV: TextView
        val productCharacImg: ImageView

        init {
            productCharacNameTV = view.findViewById(R.id.product_charac_name)
            productCharacValueTV = view.findViewById(R.id.product_charac_value)
            productCharacImg = view.findViewById(R.id.product_charac_img)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_product_characteristic,
            parent,
            false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentProductCharacteristic = this.list[position]
        holder.productCharacNameTV.text = currentProductCharacteristic.characName
        holder.productCharacValueTV.text = currentProductCharacteristic.characValue
        holder.productCharacImg.setImageResource(currentProductCharacteristic.chararImage)
    }

}