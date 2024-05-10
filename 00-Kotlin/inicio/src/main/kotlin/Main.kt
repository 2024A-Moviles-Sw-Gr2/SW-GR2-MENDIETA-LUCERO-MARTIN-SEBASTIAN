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

    // When (Switch)
    val estadoCivilWhen = 'C'
    when (estadoCivilWhen) {
        ('C') -> {
            println("Casado")
        }
        ('S') -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == 'S')
    val coqueteo = if (esSoltero) "Si" else "No" // if - else chiquito

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)

    //Named Parameters
    //calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 30.00, sueldo = 10.00, tasa = 14.00)
}

// void -> Unit (No devuelve nada)
fun imprimirNombre(nombre:String): Unit {
    println("Nombre ${nombre}")
}

fun calcularSueldo(
    sueldo:Double, //Requerido
    tasa:Double = 12.00, //Opcional (valor por defecto)
    bonoEspecial:Double? = null //Opcional (nullable)
): Double {
    if(bonoEspecial == null) {
        return sueldo * (tasa / 100)
    } else {
        return sueldo * (tasa / 100) * bonoEspecial
    }
}

abstract class NumerosJava {
    protected val numeroUno:Int
    private val numeroDos:Int
    constructor(
        uno:Int,
        dos:Int
    ) {
        this.numeroUno = uno
        this.numeroDos = dos
    }
}

abstract class Numeros(
    //Constructor primario
    // Caso 1) Parámetro normal
    // uno:Int, es un parámetro dado que no tiene modificador de acceso

    // Caso 2) Parámetro y atributo de la clase
    // private var uno:Int, es un atributo de la clase

    protected val numeroUno:Int,
    protected val numeroDos:Int
) {
    init {
        // Boque constructor primario (OPCIONAL)
        this.numeroUno
        this.numeroDos
        println("Inicializando...")
    }
}

class Suma(
    unoParametro:Int,
    dosParametro: Int
):Numeros( //Clase padre, Numeros (extendiendo)
    unoParametro,
    dosParametro
) {
    public val soyPublicoExplicito:String = "Explicito"
    val soyPublicoImplicito:String = "Implicito"
    init {
        this.numeroUno
        this.numeroUno
        numeroUno
        numeroDos
        this.soyPublicoExplicito
        soyPublicoImplicito // this. es opcional tanto en atributos como en métodos
    }

    // public fun sumer():Int {}
    fun sumar():Int {
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total)  ----> El nombre de la clase es opcional
        agregarHistorial(total)
        return total
    }

    companion object { // Comparte atributos y funciones entre todas las instancias de la clase, similar a Static
        val pi = 3.14
        fun elevarAlCuadrado(num:Int): Int {
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma:Int) {
            historialSumas.add(valorTotalSuma)
        }
    }
}


