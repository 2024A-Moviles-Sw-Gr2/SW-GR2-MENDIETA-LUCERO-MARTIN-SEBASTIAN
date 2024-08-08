package com.example.a03_android_app

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() {

    private var allAuthors:ArrayList<AuthorEntity> = arrayListOf()
    private var adapter:ArrayAdapter<AuthorEntity>? = null
    private var selectedRegisterPosition = -1
    private lateinit var map: GoogleMap
    var mapPermissions = false

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

        requestPermission()
        startMapLogic()
    }

    private fun requestPermission() {
        val context = this.applicationContext
        val finePermissionName = android.Manifest.permission.ACCESS_FINE_LOCATION
        val coarsePermissionName = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val finePermission = ContextCompat.checkSelfPermission(context, finePermissionName)
        val coarsePermission = ContextCompat.checkSelfPermission(context, coarsePermissionName)
        val hasPermissions = finePermission == PackageManager.PERMISSION_GRANTED &&
                coarsePermission == PackageManager.PERMISSION_GRANTED
        if(hasPermissions) {
            mapPermissions = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(finePermissionName, coarsePermissionName),
                1)

        }
    }

    private fun startMapLogic() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync{ googleMap ->
            with(googleMap) {
                map = googleMap
                mapConfiguration()
                markAuthorsLocation()
            }
        }
    }

    private fun mapConfiguration() {
        val context = this.applicationContext
        with(map) {
            val finePermissionName = android.Manifest.permission.ACCESS_FINE_LOCATION
            val coarsePermissionName = android.Manifest.permission.ACCESS_COARSE_LOCATION
            val finePermission = androidx.core.content.ContextCompat.checkSelfPermission(context, finePermissionName)
            val coarsePermission = androidx.core.content.ContextCompat.checkSelfPermission(context, coarsePermissionName)
            val hasPermissions = (finePermission == android.content.pm.PackageManager.PERMISSION_GRANTED) &&
                    (coarsePermission == android.content.pm.PackageManager.PERMISSION_GRANTED)
            if(hasPermissions) {
                map.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    private fun markAuthorsLocation() {
        val zoom = 10f
        var auxiliarMarkerTitle = ""
        var auxiliarLatLng:LatLng? = null

        this.allAuthors.forEach {
            auxiliarLatLng = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
            auxiliarMarkerTitle = it.name
            map.addMarker(
                MarkerOptions().position(auxiliarLatLng!!).title(auxiliarMarkerTitle)
            )!!.tag = auxiliarMarkerTitle
        }
        if (this.allAuthors.size > 0) {
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    auxiliarLatLng!!, zoom
                )
            )
        }
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
                openDialog(allAuthors[selectedRegisterPosition].id)
                return true
            }
            R.id.mi_author_books -> {
                goToActivity(BooksList::class.java, allAuthors[selectedRegisterPosition])
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

    private fun openDialog(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro de eliminar el autor?")
        builder.setPositiveButton(
            "Eliminar"
        ) { _, _ ->
            DataBase.tables!!.deleteAuthor(registerIndex)
            allAuthors.clear()
            allAuthors.addAll(DataBase.tables!!.getAllAuthors())
            adapter!!.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }

}