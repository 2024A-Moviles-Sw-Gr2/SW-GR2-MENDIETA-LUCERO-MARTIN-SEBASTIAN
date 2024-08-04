package com.example.a04_recreateapp_recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProductActivity : AppCompatActivity() {

    private val productCharacteristicsList = arrayListOf<ProductCharacteristic>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
        initializeProductsCharacteristicList()
        initializeRecyclerView()
    }

    private fun initializeProductsCharacteristicList() {
        this.productCharacteristicsList.add(
            ProductCharacteristic(1, R.drawable.gog,"Platform", "GOG.COM")
        )
        this.productCharacteristicsList.add(
            ProductCharacteristic(2, R.drawable.usa,"Activate in", "USA")
        )
        this.productCharacteristicsList.add(
            ProductCharacteristic(3, R.drawable.world,"Region", "World")
        )
        this.productCharacteristicsList.add(
            ProductCharacteristic(4, R.drawable.windows,"Device", "PC")
        )
    }

    private fun initializeRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_product_characteristic)
        val adapter = RecyclerViewProductCharacteristicAdapter(
            this.productCharacteristicsList
        )
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        adapter.notifyDataSetChanged()
    }

}