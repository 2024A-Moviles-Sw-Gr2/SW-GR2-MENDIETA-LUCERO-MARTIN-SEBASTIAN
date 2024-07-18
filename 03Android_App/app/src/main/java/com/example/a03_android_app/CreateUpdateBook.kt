package com.example.a03_android_app

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CreateUpdateBook : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_update_book)

        val title = findViewById<EditText>(R.id.input_book_title)
        val description = findViewById<EditText>(R.id.input_book_description)
        val authorId = findViewById<EditText>(R.id.input_book_author_id)
        val selectedBook = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedBook", BookEntity::class.java)
        } else {
            intent.getParcelableExtra<BookEntity>("selectedBook")
        }
        val selectedAuthor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedAuthor", AuthorEntity::class.java)
        } else {
            intent.getParcelableExtra<AuthorEntity>("selectedAuthor")
        }
        val create = intent.getBooleanExtra("create", true)

        if(create) {
            authorId.setText(selectedAuthor!!.id.toString())

            // Create a book
            val btnCreateUpdateBook = findViewById<Button>(R.id.btn_create_update_book)
            btnCreateUpdateBook.setOnClickListener{
                DataBase.tables!!.createBook(
                    title.text.toString(),
                    description.text.toString(),
                    authorId.text.toString().toInt()
                )
                goToActivity(BooksList::class.java, selectedAuthor)
            }
        } else {
            title.setText(selectedBook!!.title)
            description.setText(selectedBook.description)
            authorId.setText(selectedBook.author_id.toString())

            // Update a book
            val btnCreateUpdateBook = findViewById<Button>(R.id.btn_create_update_book)
            btnCreateUpdateBook.setOnClickListener{
                DataBase.tables!!.updateBook(
                    selectedBook.id,
                    title.text.toString(),
                    description.text.toString(),
                    authorId.text.toString().toInt()
                )
                goToActivity(BooksList::class.java, selectedAuthor!!)
            }
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: AuthorEntity
    ) {
        val intent = Intent(this, activityClass)
        intent.apply {
            putExtra("selectedAuthor", dataToPass)
        }
        startActivity(intent)
    }

}