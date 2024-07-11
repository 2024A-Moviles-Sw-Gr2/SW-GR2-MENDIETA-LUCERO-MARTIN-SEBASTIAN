package com.example.a2024aswgr2_mm

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    contexto: Context? //this
): SQLiteOpenHelper(contexto, "móviles", null, 1) {

    override fun onCreate(db: SQLiteDatabase?){
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearEntrenador(
        nombre: String,
        descripcion: String
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoGuardar = basedatosEscritura.insert(
            "ENTRENADOR", // Nombre de la tabla
            null,
            valoresAGuardar // Valores a insertar en la tabla
        )
        basedatosEscritura.close()
        return resultadoGuardar.toInt() != -1
    }
    fun eliminarEntrenadorFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        // Consulta SQL: where .... ID=? AND NOMBRE=? [?=1, ?=2]
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura.delete(
            "ENTRENADOR",
            "id=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return resultadoEliminacion.toInt() != -1
    }

    fun actualizarEntrenadorFormulario(nombre:String, descripcion:String, id:Int):Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura.update(
            "ENTRENADOR",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )
        conexionEscritura.close()
        return resultadoActualizacion.toInt() != -1
    }

    fun consultarEntrenadorPorID(id:Int):BEntrenador?{
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM ENTRENADOR WHERE ID = ?
        """.trimIndent()
        val arregloParametrosConsultaLectura = arrayOf(
            id.toString()
        )
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            arregloParametrosConsultaLectura
        )
        // Lógica de búsqueda
        // Recibimos un arreglo que puede ser nulo
        // Llenar un arreglo de Entrenadores
        // Si está vacío, el arreglo no tiene elementos
        val existeAlMenosUno = resultadoConsultaLectura.moveToFirst()
        val arregloRespuesta = arrayListOf<BEntrenador>()
        if(existeAlMenosUno) {
            do{
                val entrenador = BEntrenador(
                    // La consulta devuelve un arreglo con cada valor en el mismo orden que las
                    // columnas en la base de datos
                    resultadoConsultaLectura.getInt(0),
                    resultadoConsultaLectura.getString(1),
                    resultadoConsultaLectura.getString(2)
                )
                arregloRespuesta.add(entrenador)
            } while(resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return arregloRespuesta[0]
    }
}