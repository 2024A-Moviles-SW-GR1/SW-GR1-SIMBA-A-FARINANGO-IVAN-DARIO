package Logica


import java.io.File
import java.text.SimpleDateFormat


class ConcesionariaCrud (private val concesionariaCsvFile: String, private val autoCrud: AutoCrud) {

    private val dateFormatInput = SimpleDateFormat("yyyy-MM-dd")
    private val dateFormat= SimpleDateFormat("yyyy-MM-dd")

    fun crearConcesionaria(concesionaria: Concesionaria){
        val file = File(concesionariaCsvFile)
        if (!file.exists()){
            file.createNewFile()
        }
        val newId = generateNewId()
        val newLine = "${newId},${concesionaria.nombre},${concesionaria.ubicacion},${concesionaria.internacional},${dateFormatInput.format(concesionaria.fechaApertura)}\n"
        file.appendText(newLine)
    }

    fun readConcesionaria(): List<Concesionaria> {
        val file = File(concesionariaCsvFile)
        if (!file.exists()) {
            return emptyList()
        }

        val concesionarias = mutableListOf<Concesionaria>()

        file.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val tokens = line.split(",")
                if (tokens.size >= 5) {
                    try {
                        val id = tokens[0].toInt()
                        val nombre = tokens[1]
                        val ubicacion = tokens[2]
                        val internacional = tokens[3].toBoolean()
                        val fechaAperturaStr = tokens[4]
                        val fechaApertura = dateFormatInput.parse(fechaAperturaStr)
                        val autos = autoCrud.read().filter { it.idConcesionaria == id }.toMutableList()
                        val concesionaria = Concesionaria(id, nombre, ubicacion, internacional, fechaApertura, autos)
                        concesionarias.add(concesionaria)
                    } catch (e: Exception) {
                        println("Error al procesar la línea: $line. Saltando esta línea.")
                    }
                }
            }
        }

        return concesionarias
    }

    fun updateConcesionaria(concesionaria: Concesionaria){
        val concesionarias = readConcesionaria().toMutableList()
        val index = concesionarias.indexOfFirst { it.id == concesionaria.id }
        if (index!= -1){
            concesionarias[index] = concesionaria
            saveAllConcesionarias(concesionarias)
        }
    }

    fun deleteConcesionaria(id: Int) {
        val concesionarias = readConcesionaria().filter { it.id != id }
        saveAllConcesionarias(concesionarias)
    }



    private fun saveAllConcesionarias(concesionarias: List<Concesionaria>) {
        val file = File(concesionariaCsvFile)
        file.writeText(concesionarias.joinToString("\n") { concesionaria ->
            "${concesionaria.id},${concesionaria.nombre},${concesionaria.ubicacion},${concesionaria.internacional},${dateFormatInput.format(concesionaria.fechaApertura)}"
        })
    }

    private fun generateNewId(): Int{
        val autos = readConcesionaria()
        return if (autos.isEmpty()){
            1
        } else {
            autos.maxOf { it.id } + 1
        }
    }
}