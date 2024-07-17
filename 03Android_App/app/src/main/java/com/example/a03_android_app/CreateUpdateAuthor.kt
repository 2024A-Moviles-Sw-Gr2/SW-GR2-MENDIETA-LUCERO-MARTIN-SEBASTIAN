package com.example.a03_android_app

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
        val mainActivity = MainActivity()
        val selectedAuthor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("DATA", AuthorEntity::class.java)
        } else {
            intent.getParcelableExtra<AuthorEntity>("DATA")
        }

        if(selectedAuthor == null) {
            // Create an author
            val btnCreateUpdateAuthor = findViewById<Button>(R.id.btn_create_update_author)
            btnCreateUpdateAuthor.setOnClickListener{
                val createAuthorResponse = DataBase.tables!!.createAuthor(
                    name.text.toString(),
                    age.text.toString().toInt(),
                    literaryGenre.toString()
                )
                mainActivity.updateAllAuthorsList()

                if(createAuthorResponse) showSnackBar("Autor ${name.text} creado")
            }
        } else {
            name.setText(selectedAuthor.name)
            age.setText(selectedAuthor.age.toString())
            literaryGenre.setText(selectedAuthor.literary_genre)

            // Update an author
            val btnCreateUpdateAuthor = findViewById<Button>(R.id.btn_create_update_author)
            btnCreateUpdateAuthor.setOnClickListener{
                val updateAuthorResponse = DataBase.tables!!.updateAuthor(
                    selectedAuthor.id,
                    name.text.toString(),
                    age.text.toString().toInt(),
                    literaryGenre.toString()
                )
                mainActivity.updateAllAuthorsList()

                if(updateAuthorResponse) showSnackBar("Autor ${name.text} actualizado")
            }
        }

    }

    private fun showSnackBar(text: String) {
        val snack = Snackbar.make(
            findViewById(R.id.view_create_update_author),
            text,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}