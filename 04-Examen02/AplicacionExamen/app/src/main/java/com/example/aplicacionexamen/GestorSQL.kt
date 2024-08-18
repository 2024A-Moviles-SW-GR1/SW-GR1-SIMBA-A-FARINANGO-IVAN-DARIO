package com.example.aplicacionexamen
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GestorSQL(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "AppDatabase.db"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_CONCESIONARIO = """
            CREATE TABLE Concesionario (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                fechaInaguracion TEXT
            )
        """

        private const val SQL_CREATE_TABLE_AUTOS = """
            CREATE TABLE Auto (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                detalle TEXT,
                ubicacion TEXT,
                clienteId INTEGER,
                FOREIGN KEY(clienteId) REFERENCES Concesionario(id)
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_CONCESIONARIO)
        db.execSQL(SQL_CREATE_TABLE_AUTOS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Aquí puedes manejar las actualizaciones de la base de datos
    }

    // CRUD Operaciones para Concesionario
    fun addCliente(nombre: String, apellido: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("fechaInaguracion", apellido)
        }
        return db.insert("Concesionario", null, values)
    }

    fun getConcesionarios(): MutableList<Concesionario> {
        val db = this.readableDatabase
        val projection = arrayOf("id", "nombre", "fechaInaguracion")
        val cursor = db.query("Concesionario", projection, null, null, null, null, null)
        val concesionarios = mutableListOf<Concesionario>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val nombre = getString(getColumnIndexOrThrow("nombre"))
                val fechaInaguracion = getString(getColumnIndexOrThrow("fechaInaguracion"))
                concesionarios.add(Concesionario(id, nombre, fechaInaguracion))
            }
        }
        cursor.close()
        return concesionarios
    }



    fun updateConcesionario(id: Int, nombre: String, fechaInaguracion: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("nombre", nombre)
            put("fechaInaguracion", fechaInaguracion)
        }
        return db.update("Concesionario", values, "id=?", arrayOf(id.toString()))
    }

    fun deleteCliente(id: Int): Int {
        val db = this.writableDatabase
        return db.delete("Concesionario", "id=?", arrayOf(id.toString()))
    }

    // CRUD Operaciones para Factura
    fun addAuto(detalle: String, ubicacion: String, clienteId: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("detalle", detalle)
            put("ubicacion", ubicacion)
            put("clienteId", clienteId)
        }
        return db.insert("Auto", null, values)
    }

    fun getAuto(): MutableList<Auto> {
        val db = this.readableDatabase
        val projection = arrayOf("id", "detalle", "ubicacion", "clienteId")
        val cursor = db.query("Auto", projection, null, null, null, null, null)
        val autos = mutableListOf<Auto>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val detalle = getString(getColumnIndexOrThrow("detalle"))
                val ubicacion = getString(getColumnIndexOrThrow("ubicacion"))
                val clienteId = getInt(getColumnIndexOrThrow("clienteId"))
                autos.add(Auto(id, detalle, ubicacion, clienteId))
            }
        }
        cursor.close()
        return autos
    }

    fun updateAuto(id: Int, detalle: String, ubicacion: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("detalle", detalle)
            put("ubicacion", ubicacion)
        }
        return db.update("Auto", values, "id=?", arrayOf(id.toString()))
    }

    fun deleteAuto(id: Int): Int {
        val db = this.writableDatabase
        return db.delete("Auto", "id=?", arrayOf(id.toString()))
    }
}
