package com.example.a2024aswgr2_mm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.snackbar.Snackbar

class ACicloVida : AppCompatActivity() {

    // Se resetea debido a cambios en el HW, como cambiar de Landscape a Portrait
    var textoGlobal = ""

    fun mostrarSnackBar(texto: String) {
        textoGlobal += texto
        val snack = Snackbar.make(
            findViewById(R.id.cl_ciclo_vida),
            textoGlobal,
            Snackbar.LENGTH_INDEFINITE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aciclo_vida)
        mostrarSnackBar("onCreate")
    }

    override fun onStart() {
        super.onStart()
        mostrarSnackBar("onStart")
    }

    override fun onResume() {
        super.onResume()
        mostrarSnackBar("onResume")
    }

    override fun onRestart() {
        super.onRestart()
        mostrarSnackBar("onRestart")
    }

    override fun onPause() {
        super.onPause()
        mostrarSnackBar("onPause")
    }

    override fun onStop() {
        super.onStop()
        mostrarSnackBar("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackBar("onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            // GUARDAR LAS PRIMITIVAS
            putString("variableTextoGuardado", textoGlobal)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // RECUPERAR LA VARIABLE PRIMITIVA
        val textoRecuperadoDeVariable: String? = savedInstanceState.getString("variableTextoGuardado")
        if (textoRecuperadoDeVariable != null) {
            mostrarSnackBar(textoRecuperadoDeVariable)
            textoGlobal = textoRecuperadoDeVariable
        }
    }

}