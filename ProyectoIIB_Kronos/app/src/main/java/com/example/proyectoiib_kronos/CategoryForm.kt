package com.example.proyectoiib_kronos


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

class CategoryForm : AppCompatActivity() {

    var iconSelected: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_category_form)

        val inputCategoryName = findViewById<TextInputEditText>(R.id.input_category_name)
        val btnSaveCategory = findViewById<Button>(R.id.btn_save_category)

        btnSaveCategory.setOnClickListener {
            Database.data!!.createCategory(
                inputCategoryName.text.toString()
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