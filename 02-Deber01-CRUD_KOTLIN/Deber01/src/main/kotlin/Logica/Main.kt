package Logica

import java.text.SimpleDateFormat
import java.util.*

fun main() {
    val autoCrud = AutoCrud("auto.csv")
    val concesonariaCrud = ConcesionariaCrud("concesionaria.csv", autoCrud)
    val scanner = Scanner(System.`in`)

//    MENU
    var opcion: Int = 0;
    while(opcion != 9){
        mostrarMenu();
//        Leer opcion
        opcion = readLine()!!.toInt();
        when(opcion){
            1 -> {
//                Mostrar autos
                println(autoCrud.read())
            }
            2 -> {
//                Mostrar Concesionaria con autos
                println(concesonariaCrud.readConcesionaria())
            }
            3 -> {
//                Mostrar lista de Concesionaria
                val listaConcesionarias = concesonariaCrud.readConcesionaria()
                println("Concesionarias Disponibles:")
                listaConcesionarias.forEach { println("${ it.id }:${it.nombre}") }
            }
            4 -> {
//                Ingresar nuevo auto
                println("Ingrese la marca del auto:")
                val marca = scanner.nextLine()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                println("Ingrese el modelo del auto:")
                val modelo = scanner.nextLine()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                println("El auto es usado? (si-no)")
                val usadoStr = scanner.nextLine()
                val usado = if (usadoStr == "si") true else false
                println("Ingrese el precio del auto:")
                val precio = scanner.nextDouble()
                scanner.nextLine()



                val listaConcesionaria = concesonariaCrud.readConcesionaria()
                println("Concesionarias Disponibles:")
                listaConcesionaria.forEach { println("${ it.id }:${it.nombre}") }
                println("Escoge la concesionaria: ")
                val concesionaria = scanner.nextInt()
                scanner.nextLine()

                val nuevoAuto = Auto(
                    id = 0,
                    marca = marca,
                    modelo = modelo,
                    usado = usado,
                    precio = precio,
                    idConcesionaria = concesionaria
                )
                autoCrud.create(nuevoAuto)
                println("Auto creado!")

                val concesionarias = listaConcesionaria.find { it.id == concesionaria }
                if (concesionarias != null) {
                    concesionarias.autos.add(nuevoAuto)
                    concesonariaCrud.updateConcesionaria(concesionarias)
                }
            }
            5 -> {
//                Modificar auto
                println(autoCrud.read())
                println("Ingrese el N. del auto que desea actualizar:")
                val autoId = scanner.nextInt()
                scanner.nextLine() // Consumir la nueva línea

                val auto = autoCrud.read().find { it.id == autoId }
                if (auto != null) {
                    println("Ingrese el nuevo precio del auto (o presione Enter para mantener el actual):")
                    val precioStr = scanner.nextLine()
                    val nuevoPrecio = if (precioStr.isNotBlank()) precioStr.toDouble() else auto.precio

                    println("¿El auto es usado? (si/no, o presione Enter para mantener el actual):")
                    val usadoStr = scanner.nextLine()
                    val nuevoUsado = if (usadoStr.isNotBlank()) usadoStr.equals("si", ignoreCase = true) else auto.usado

                    val autoActualizado = auto.copy(
                        precio = nuevoPrecio,
                        usado = nuevoUsado
                    )

                    autoCrud.update(autoActualizado)
                    println("Auto actualizado correctamente.")
                } else {
                    println("No se encontró un auto con ID: $autoId")
                }

            }
            6 -> {
//                Eliminar auto
                println(autoCrud.read())
                println("Ingrese el N. del auto que desea eliminar:")
                val jugadorId = scanner.nextInt()
                autoCrud.delete(jugadorId)
                println("auto eliminado correctamente!")
            }
            7 -> {
//                Ingresar nueva concesionaria
                println("Ingrese el nombre de la concesionaria:")
                val nombre = scanner.nextLine().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                println("Ingrese la ubicacion de la concesionaria:")
                val ubicacion = scanner.nextLine().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                println("La concesonaria es internacional?(si/no):)")
                val internacionalStr = scanner.nextLine()
                val internacional = if (internacionalStr =="si") true else false
                println("Ingrese la fecha de apertura de la concesionaria")
                val fechaAperturaStr = scanner.nextLine()
                val fechaApertura = SimpleDateFormat("yyyy-MM-dd").parse(fechaAperturaStr)






                val nuevaConcesionaria = Concesionaria(
                    id = 0,
                    nombre = nombre,
                    ubicacion = ubicacion,
                    internacional = internacional,
                    fechaApertura = fechaApertura
                )
                concesonariaCrud.crearConcesionaria(nuevaConcesionaria)
                println("Concesionaria creada correctamente!")
            }
            8 -> {
//                Eliminar concesionaria
                println(concesonariaCrud.readConcesionaria())
                println("Ingrese el N. de la concesionaria  que desea eliminar:")
                val concesionariaId = scanner.nextInt()
                concesonariaCrud.deleteConcesionaria(concesionariaId)
                println("Concesonaria eliminada correctamente")
            }
            9 -> {
//                Salir
                println("Adios!")
                break;
            }
            else -> continue
        }
    }

}

fun mostrarMenu(){
    println("\n\t"+ "******* Venta de autos *******\n" +
            "1. Mostrar autos\n"+
            "2. Mostrar Concesionaria con sus autos\n"+
            "3. Mostrar Lista de Concesionaria\n"+
            "4. Ingresar nuevo auto\n"+
            "5. Modificar auto\n"+
            "6. Eliminar auto\n"+
            "7. Ingresar nuevo concesionaria\n"+
            "8. Eliminar concesionaria\n"+
            "9. Salir\n"+
            "Ingresa tu opcion: ")
}

