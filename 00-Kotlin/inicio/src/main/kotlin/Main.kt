import java.util.*

fun main() {
    println("Hola Mundo!")

    // Inmutables (No se puede reasignar) ---------------------------
    val inmutable: String = "Martin"

    // Mutables ---------------------------
    var mutable: String = "Mendieta"
    mutable = "Diablo"

    // Duck Typing ---------------------------
    // Ej: "Si se ve como un String, si funciona como un String, es un String"
    // No es necesario declarar el tipo de dato de una variable
    var ejemploVariable = "Martin el Diablo"
    var edadEjemplo = 21

    // Variables primitivas ---------------------------
    val nombre:String = "Diavlo"
    val sueldo:Double = 12.5
    val estadoCivil:Char = 'C'
    val mayorEdad:Boolean = true

    // Clases en Java ---------------------------
    val fechaNacimiento:Date = Date()
}