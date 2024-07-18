package com.example.a03_android_app

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class BooksList : AppCompatActivity() {

    private var allBooks:ArrayList<BookEntity> = arrayListOf()
    private var adapter:ArrayAdapter<BookEntity>? = null
    private var selectedRegisterPosition = -1
    private var selectedAuthor: AuthorEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books_list)


        selectedAuthor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("selectedAuthor", AuthorEntity::class.java)
        } else {
            intent.getParcelableExtra<AuthorEntity>("selectedAuthor")
        }
        val booksAuthor = findViewById<TextView>(R.id.txt_view_author_name)
        booksAuthor.text = selectedAuthor!!.name

        DataBase.tables = SqliteHelper(this)
        val booksList = findViewById<ListView>(R.id.list_books)
        allBooks = DataBase.tables!!.getBooksByAuthor(selectedAuthor!!.id)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            allBooks
        )

        booksList.adapter = adapter
        adapter!!.notifyDataSetChanged() // To update the UI

        val btnRedirectCreateBook = findViewById<Button>(R.id.btn_redirect_create_book)
        btnRedirectCreateBook.setOnClickListener{
            goToActivity(CreateUpdateBook::class.java, null, selectedAuthor!!)
        }
        val btnBackToMain = findViewById<Button>(R.id.btn_back_to_main)
        btnBackToMain.setOnClickListener{
            goToActivity(MainActivity::class.java)
        }

        registerForContextMenu(booksList)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        // Set options for the context menu
        val inflater = menuInflater
        inflater.inflate(R.menu.book_menu, menu)

        // Get ID of the selected item of the list
        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        selectedRegisterPosition = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_edit_book -> {
                goToActivity(CreateUpdateBook::class.java, allBooks[selectedRegisterPosition], selectedAuthor!!, false)
                return true
            }
            R.id.mi_delete_book -> {
                openDialog(allBooks[selectedRegisterPosition].id)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: BookEntity? = null,
        dataToPass2: AuthorEntity? = null,
        create: Boolean = true
    ) {
        val intent = Intent(this, activityClass)
        if(dataToPass != null) {
            intent.apply {
                putExtra("selectedBook", dataToPass)
            }
        }
        if(dataToPass2 != null) {
            intent.apply {
                putExtra("selectedAuthor", dataToPass2)
            }
        }
        intent.apply {
            putExtra("create", create)
        }
        startActivity(intent)
    }

    private fun openDialog(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro de eliminar el libro?")
        builder.setPositiveButton(
            "Eliminar"
        ) { _, _ ->
            DataBase.tables!!.deleteBook(registerIndex)
            allBooks.clear()
            allBooks.addAll(DataBase.tables!!.getBooksByAuthor(selectedAuthor!!.id))
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }

}