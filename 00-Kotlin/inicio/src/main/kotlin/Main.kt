import java.util.*

fun main() {
    println("Hola mundo")

    // Inmutables (No se pueden re asignan "'="）
    val inmutable: String = "Ivan"
    // inmutable = "Simbaña" // ERROR!
    // Mutables
    var mutable: String = "Ivan"
    mutable = "Simbaña" // 0K
    //val>var

    // Duck Typing
    var ejemploVariable = " Ivan Simbaña"
    val edadEsemplo: Int = 12
    ejemploVariable.trim() // Para borra espacios
    // ejemploVariable = edadEiemala // ERROR!

    // Variables primitivas
    val nombre: String = "Ivan"
    val sueldo: Double = 1.2
    val estadaCivil: Char = 'S'
    val mayorEdad: Boolean = true
    // clases en Java
    val fechaNacimiento: Date = Date()

    // When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen) {
        "C" -> {
            println("Casado")
        }

        "S" -> {
            println("Soltero")
        }

        else -> {
            println("No sabemos")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" //If else chiquito


    // Interpolacion de Strings
    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    //Named Parameters
    //calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(sueldo = 10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    // Uso de clases
    val sumaUno = Suma(1,1) // new Suma(1,1) en KOTLIN no hay "new"
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar ()
    sumaTres.sumar ()
    sumaCuatro.sumar ()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //Arreglos
    //Estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico);

    //Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(1,2,3,4,5,6,7,8,9,10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    //For each => Unit
    //Itera el arreglo

    val respuestaForEach = arregloDinamico
        .forEach { valorActual: Int ->
            println("Valor actual: $valorActual")
        }

    // "it" (en ingles "eso") significa el elemento iterado
    arregloDinamico.forEach{ println("Valor Actual (it): ${it}")}

    // MAP -> MUTA(Modifica cambia) el arreglo
    // 1) Enviamos el nuevo valor de la iteracion
    // 2) Nos devuelve un NUEVO ARREGLO con valores
    // de las iteraciones
    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 100.00
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map{ it + 15 }
    println(respuestaMapDos)

    // Filter - > Filtrar el ARREGLO
    // 1) Devolver una expresion (TRUE o FALSE)
    // 2) Nuevo arreglo FILTRADO
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            // Expresion o CONDICION
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco

        }

    val respuestaFilterDos = arregloDinamico.filter{ it <= 5 } // < =
    println(respuestaFilter)
    println(respuestaFilterDos)

    // ANY -> OR (o) -> Devuelve TRUE o FALSE
    // ALL -> AND (y) -> Devuelve TRUE o FALSE
    // AND -> TRUE, TRUE = TRUE
    // OR -> TRUE, FALSE = TRUE
    // OR -> FALSE, TRUE = TRUE
    // OR -> FALSE, FALSE = FALSE
    // ALL -> TRUE, TRUE = TRUE
    // ALL -> TRUE, FALSE = FALSE
    // ALL -> FALSE, TRUE = FALSE
    // ALL -> FALSE, FALSE = FALSE

    // OR -> SOME (ALGUNOS CUMPLEN)
    // ALL -> EVERY (TODOS CUMPLEN)
    val respuestaAny: Boolean = arregloDinamico
        .any { valorActual: Int ->
            return@any (valorActual > 5)
        }

    println(respuestaAny)//TRUE

    val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)
        }

    println(respuestaAll)//FALSE

    // REDUCE -> Valor acumulado
    // Valor acumulado = 0 (Siempre empieza en 0 en Kotlin)
    // [1,2,3,4,5] -> Acumular "SUMAR" estos valores del arreglo
    // valorIteracion1 = valorEmpieza + 1 = 0 +1 = 1 -> Iteracion1
    // valorIteracion2 = valorAcumuladoIteracion1 + 2 = 1 + 2 = 3 -> Iteracion2
    // valorIteracion3 = valorAcumuladoIteracion2 + 3 = 3 + 3 = 6 -> Iteracion3
    // valorIteracion4 = valorAcumuladoIteracion3 + 4 = 6 + 4= 10 -> Iteracion4
    // valorIteracion5 = valorAcumuladoIteracion4 + 5 = 10 + 5 = 15 -> Iteracion4
    val respuestaReduce: Int = arregloDinamico
        .reduce { acumulado: Int, valorAcual: Int ->
            return@reduce (acumulado + valorAcual) // -> Cambiar o usar la Logica de negocio
        }

    println(respuestaReduce);
    // return@reduce acumulado + (itemCarrito.cantidad * itemCarrito.precio)
    // return@filter mayoresACinco

}



//void -> Unit
fun imprimirNombre(nombre: String): Unit {
    println("Nombre: $nombre")//String template
}

fun calcularSueldo(
    sueldo: Double, //Requeridos
    tasa: Double = 12.00, //Opcionales (defecto)
    bonoEspecial: Double? = null //Opcionales (pueden ser nulos)
): Double {
    //Int -> Int? (nullable)
    //String -> String? (nullable)
    //Date -> Date? (nullable)
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) * bonoEspecial
    }
}


abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int, dos: Int
    ) {
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }

}


abstract class Numeros(
    // Constructor Primar1o
    // Caso 1) Parametro normal
    // uno:Int , (parametro (sin modificador acceso))

    // Caso 2) Parametro y propiedad (atributo) (private)
    // private var uno: Int (propiedad "instancia.uno")


    protected val numeroUno: Int, // instancia.numeroUno
    protected val numeroDos: Int, // instancia.numeroDos
    // parametroInutil: String //parametro no se usa
) {
    init { // bloque constructor primario
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}


class Suma(
    // Constructor primario
    unoParametro: Int, // Parametro
    dosParametro: Int, // Parametros
) : Numeros( // Clase papa, Numeros (extendiendo)
    unoParametro,
    dosParametro
) {
    public val soyPublicoExplicito: String = "Explicito" // Publicas
    val soyPublicoImplicito: String = "Implicito" // Publicas (propiedades, metodos)

    init { // Bloque Codigo Constructor primario
        this.numeroUno
        this.numeroDos
        numeroUno // this. OPCIONAL (propiedades, metodos)
        numeroDos // this. OPCIONAL (propiedades, metodos)
        this.soyPublicoExplicito
        soyPublicoImplicito // this. OPCIONAL (propiedades, metodos)
    }

    constructor( // Constructor secundario
        uno: Int?,
        dos: Int
    ) : this(
        if (uno == null) 0 else uno,
        dos
    )


    constructor( // Constructor tercero
        uno: Int,
        dos: Int?
    ) : this(
        uno,
        if (dos == null) 0 else dos,
    )


    constructor( // Constructor cuarto
        uno: Int?,
        dos: Int?
    ) : this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos,
    )

    // public fun sumar():Int{

    // public fun sumar()Int{ (Modificar "public" es OPCIONAL
    fun sumar(): Int {
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total) ("Suma." o "NombreClase." es OPCIONAL)
        agregarHistorial(total)

        return total
    }

    companion object { // Comparte entre todas las instancias, similar al Static
        // funciones y variables
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }

        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma: Int) {
            historialSumas.add(valorTotalSuma)
        }
    }

}




