package com.example.a04_recreateapp_recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val productsList = arrayListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeProductsList()
        initializeRecyclerView()
    }

    private fun initializeProductsList() {
        this.productsList.add(
            Product(1, R.drawable.steam,"Random ELITE 1 Key - Steam", "$ 20.23", "$ 80.56", "-80%")
        )
        this.productsList.add(
            Product(2, R.drawable.alone_dark,"Alone in the Dark", "$ 15.00", "$ 60.00", "-75%")
        )
        this.productsList.add(
            Product(3, R.drawable.dead_space,"Dead Space - Pro Pack", "$ 10.00", "$ 40.00", "-70%")
        )
        this.productsList.add(
            Product(4, R.drawable.fallout,"Fallout - Survival Kit", "$ 25.00", "$ 100.00", "-75%")
        )
        this.productsList.add(
            Product(5, R.drawable.noire,"Noire - Ultimate Pass", "$ 30.00", "$ 120.00", "-75%")
        )
        this.productsList.add(
            Product(6, R.drawable.prototype,"Prototype - Premium Pack", "$ 12.50", "$ 50.00", "-75%")
        )
        this.productsList.add(
            Product(7, R.drawable.obscure,"Obscure - Red Blood Edition", "$ 18.00", "$ 72.00", "-75%")
        )
    }

    private fun initializeRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_product_cards)
        val adapter = RecyclerViewProductAdapter(
            this,
            this.productsList
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

    fun goToActivity(
        activity: Class<*>
    ) {
        val intent = Intent(this, activity)
        startActivity(intent)
    }
}