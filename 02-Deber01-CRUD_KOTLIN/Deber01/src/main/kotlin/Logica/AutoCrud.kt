package Logica

import java.io.File
import java.text.SimpleDateFormat

class AutoCrud (val csvFile : String) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    fun create(auto: Auto) {
        val file = File(csvFile)
        if (!file.exists()) {
            file.createNewFile()
        }
        val newId = generateNewId()
        val newLine = "${newId},${auto.marca},${auto.modelo},${auto.usado},${auto.precio},${auto.idConcesionaria}\n"
        file.appendText(newLine)
    }

    fun read(): List<Auto> {
        val file = File(csvFile)
        if(!file.exists()){
            return emptyList()
        }
        val autosLeidos = mutableListOf<Auto>()
        file.forEachLine { line ->
            val tokens = line.split(",")

            if (tokens.size == 6) {

                val id = tokens[0].toInt()
                val marca = tokens[1]
                val modelo = tokens[2]
                val usado = tokens[3].toBoolean()
                val precio = tokens[4].toDouble()
                val concesionariaId = tokens[5].toInt()
                val auto = Auto(id, marca, modelo, usado, precio,concesionariaId)
                autosLeidos.add(auto)
            }
        }
        return autosLeidos
    }

    fun update(auto: Auto) {
        val autos = read().toMutableList()
        val index = autos.indexOfFirst { it.id == auto.id }
        if (index != -1) {
            autos[index] = auto
            saveAll(autos)
        }
    }

    fun delete(id: Int) {
        val autos = read().filter { it.id != id }
        saveAll(autos)
    }

    private fun saveAll(autos: List<Auto>) {
        val file = File(csvFile)
        file.writeText(autos.joinToString("\n") { auto ->
            "${auto.id},${auto.marca},${auto.modelo},${auto.usado},${auto.precio},${auto.idConcesionaria}"
        }+"\n")
    }

    private fun generateNewId(): Int {
        val autos = read()
        return if (autos.isEmpty()){
            1
        } else {
            autos.maxOf { it.id } +1
        }
    }
}