package Logica

data class Auto(
    var id : Int,
    var marca: String,
    var modelo: String,
    var usado: Boolean,
    var precio: Double,
    var idConcesionaria: Int
) {
    override fun toString(): String {
        val concesionariaNombre = obtenerNombreConcesionaria(idConcesionaria)
        return "\n"+"""
            |Auto numero: $id
            |Marca: $marca
            |Modelo: $modelo
            |Usado: ${if (usado) "Si" else "No"}
            |Precio: $precio
            |Concesionaria: $concesionariaNombre
        """.trimMargin() + "\n"
    }

    private fun obtenerNombreConcesionaria(concesionariaId :Int):String{
        val concesionariaCRUD = ConcesionariaCrud("concesionaria.csv", AutoCrud("auto.csv"))
        val concesionaria = concesionariaCRUD.readConcesionaria().find { it.id.toInt() == concesionariaId }
        return concesionaria?.nombre ?: "Desconocido"
    }
}