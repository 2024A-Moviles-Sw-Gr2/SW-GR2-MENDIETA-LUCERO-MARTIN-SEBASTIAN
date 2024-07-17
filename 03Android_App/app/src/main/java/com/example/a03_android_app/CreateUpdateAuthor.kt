package com.example.a03_android_app

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class CreateUpdateAuthor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_update_author)

        val name = findViewById<EditText>(R.id.input_author_name)
        val age = findViewById<EditText>(R.id.input_author_age)
        val literaryGenre = findViewById<EditText>(R.id.input_author_literary_genre)
        val selectedAuthor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedAuthor", AuthorEntity::class.java)
        } else {
            intent.getParcelableExtra<AuthorEntity>("selectedAuthor")
        }

        if(selectedAuthor == null) {
            // Create an author
            val btnCreateUpdateAuthor = findViewById<Button>(R.id.btn_create_update_author)
            btnCreateUpdateAuthor.setOnClickListener{
                DataBase.tables!!.createAuthor(
                    name.text.toString(),
                    age.text.toString().toInt(),
                    literaryGenre.text.toString()
                )
                goToActivity(MainActivity::class.java)
            }
        } else {
            name.setText(selectedAuthor.name)
            age.setText(selectedAuthor.age.toString())
            literaryGenre.setText(selectedAuthor.literary_genre)

            // Update an author
            val btnCreateUpdateAuthor = findViewById<Button>(R.id.btn_create_update_author)
            btnCreateUpdateAuthor.setOnClickListener{
                DataBase.tables!!.updateAuthor(
                    selectedAuthor.id,
                    name.text.toString(),
                    age.text.toString().toInt(),
                    literaryGenre.text.toString()
                )
                goToActivity(MainActivity::class.java)
            }
        }

    }

    private fun goToActivity(
        activityClass: Class<*>
    ) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}