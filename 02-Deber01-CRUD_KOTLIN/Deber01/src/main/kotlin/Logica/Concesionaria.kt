package Logica

import java.util.*

 data class Concesionaria(
     var id : Int,
     var nombre: String,
     var ubicacion: String,
     var internacional: Boolean,
     var fechaApertura: Date,
     var autos : MutableList<Auto> = mutableListOf()


 ) {
     override fun toString(): String {
         return "\n" +
                 "\t" + nombre.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } + "\n" +
                 "Ubicacion " + ubicacion + "\n" +
                 "Internacional: " + internacional + "\n" +
                 "Fecha de apertura: " + Fecha.formatoFecha(fechaApertura)  + "\n" +
                 "Autos:\n" + "{" + autos.joinToString() { it.toString() + "\n}" } + "\n"
     }

}