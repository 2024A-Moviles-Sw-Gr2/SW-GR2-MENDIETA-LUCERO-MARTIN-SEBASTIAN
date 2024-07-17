package com.example.a03_android_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    private var allAuthors:ArrayList<AuthorEntity> = arrayListOf()
    private var adapter:ArrayAdapter<AuthorEntity>? = null
    private var selectedRegisterPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DataBase.tables = SqliteHelper(this)
        val authorsList = findViewById<ListView>(R.id.list_authors)
        allAuthors = DataBase.tables!!.getAllAuthors()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            allAuthors
        )

        authorsList.adapter = adapter
        adapter!!.notifyDataSetChanged() // To update the UI

        val btnRedirectCreateAuthor = findViewById<Button>(R.id.btn_redirect_create_author)
        btnRedirectCreateAuthor.setOnClickListener{
            goToActivity(CreateUpdateAuthor::class.java)
        }

        registerForContextMenu(authorsList)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        // Set options for the context menu
        val inflater = menuInflater
        inflater.inflate(R.menu.author_menu, menu)

        // Get ID of the selected item of the list
        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        selectedRegisterPosition = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_edit_author -> {
                goToActivity(CreateUpdateAuthor::class.java, allAuthors[selectedRegisterPosition])
                return true
            }
            R.id.mi_delete_author -> {

                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: AuthorEntity? = null
    ) {
        val intent = Intent(this, activityClass)
        if(dataToPass != null) {
            intent.apply {
                putExtra("selectedAuthor", dataToPass)
            }
        }
        startActivity(intent)
    }

    fun updateAllAuthorsList() {
        allAuthors.clear()
        allAuthors.addAll(DataBase.tables!!.getAllAuthors())
        adapter!!.notifyDataSetChanged()
    }

}