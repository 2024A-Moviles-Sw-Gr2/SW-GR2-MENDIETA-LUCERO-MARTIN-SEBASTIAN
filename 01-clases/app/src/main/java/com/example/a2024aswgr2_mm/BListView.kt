package com.example.a2024aswgr2_mm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.android.material.snackbar.Snackbar

class BListView : AppCompatActivity() {
    val arreglo = BBaseDatosMemoria.arregloBEntrenador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        val listView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this, // contexto
            android.R.layout.simple_list_item_1, // layout xml a usar
            arreglo
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged() // Para actualizar UI
        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_list_view)
        botonAnadirListView.setOnClickListener {
            anadirEntrenador(adaptador)
        }
        registerForContextMenu(listView) // Agrega un menu contextual a la lista
                                        // Cuando se mantiene aplastado un registro se despliega un menu
                                        // con acciones para ese registro
    }

    var posicionItemSeleccionado = -1

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos de opciones el menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        // Obtener ID del item de la lista actualmente seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar -> {
                mostrarSnackBar("Editar $posicionItemSeleccionado")
                return true
            }
            R.id.mi_eliminar -> {
                mostrarSnackBar("Eliminar $posicionItemSeleccionado")
                abrirDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun abrirDialogo(){}

    fun anadirEntrenador (
        adaptador: ArrayAdapter<BEntrenador>
    ) {
        arreglo.add(
            BEntrenador(4, "Wendy", "d@d.com")
        )
        adaptador.notifyDataSetChanged()
    }

    fun mostrarSnackBar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_ciclo_vida),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
    }
}