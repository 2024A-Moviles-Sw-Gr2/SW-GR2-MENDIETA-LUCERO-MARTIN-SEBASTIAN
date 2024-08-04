package com.example.a04_recreateapp_recyclerview

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewProductAdapter(
    private val context: MainActivity,
    private val list: ArrayList<Product>
): RecyclerView.Adapter<RecyclerViewProductAdapter.MyViewHolder>() {

    inner class MyViewHolder(
        view: View
    ): RecyclerView.ViewHolder(view) {
        val productNameTV: TextView
        val productPriceTV: TextView
        val productOriginalPriceTV: TextView
        val discountTV: TextView
        val coverImage: ImageView
        private val productCard: ConstraintLayout

        init {
            productNameTV = view.findViewById(R.id.product_name)
            productPriceTV = view.findViewById(R.id.product_price)
            productOriginalPriceTV = view.findViewById(R.id.product_original_price)
            productOriginalPriceTV.paintFlags = productOriginalPriceTV.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            discountTV = view.findViewById(R.id.discount)
            coverImage = view.findViewById(R.id.product_cover_image)
            productCard = view.findViewById(R.id.recycler_view_product_card)
            productCard.setOnClickListener{ context.goToActivity(ProductActivity::class.java) }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.recycler_view_product_card,
            parent,
            false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentProduct = this.list[position]
        holder.productNameTV.text = currentProduct.productName
        holder.productPriceTV.text = currentProduct.productPrice
        holder.productOriginalPriceTV.text = currentProduct.productOriginalPrice
        holder.discountTV.text = currentProduct.discount
        holder.coverImage.setImageResource(currentProduct.coverImage)
    }

}