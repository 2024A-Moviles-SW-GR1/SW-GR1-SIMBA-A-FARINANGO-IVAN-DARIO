package com.example.aplicacionexamen


import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val gestorSQL: GestorSQL = GestorSQL(this) // Instancia de la clase GestorSQL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewConcesionario)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, loadConcesionarios())
        listView.adapter = adapter

        val crearConcesionarioButton = findViewById<Button>(R.id.crearConcesionario)
        crearConcesionarioButton.setOnClickListener {
            val intent = Intent(this, CrearConcesionarioActivity::class.java)
            startActivityForResult(intent, 1)  // Use request code to identify the result
        }

        registerForContextMenu(listView)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            adapter.clear()
            adapter.addAll(loadConcesionarios())
            adapter.notifyDataSetChanged()
        }
    }

    private fun loadConcesionarios(): MutableList<String> {
        return gestorSQL.getConcesionarios().map { it.nombre + " " + it.fechaInaguracion }.toMutableList()
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        when (item.itemId) {
            R.id.edit -> editConcesionario(info.position)
            R.id.delete -> {
                gestorSQL.deleteCliente(gestorSQL.getConcesionarios()[info.position].id)
                updateListView()
            }
            R.id.view_autos -> viewFacturas(info.position)
            else -> return super.onContextItemSelected(item)
        }
        return true
    }

    private fun editConcesionario(position: Int) {
        val client = gestorSQL.getConcesionarios()[position]
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Concesionario")
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        input.setText(client.nombre + " " + client.fechaInaguracion)
        builder.setView(input)

        builder.setPositiveButton("Guardar") { dialog, which ->
            val parts = input.text.toString().split(" ")
            gestorSQL.updateConcesionario(client.id, parts[0], parts.getOrElse(1) { "" })
            updateListView()
        }
        builder.setNegativeButton("Cancelar") { dialog, which -> dialog.cancel() }

        builder.show()
    }

    private fun updateListView() {
        adapter.clear()
        adapter.addAll(loadConcesionarios())
        adapter.notifyDataSetChanged()
    }

    private fun viewFacturas(position: Int) {
        val intent = Intent(this, AutosActivity::class.java)
        intent.putExtra("clienteId", gestorSQL.getConcesionarios()[position].id)
        startActivity(intent)
    }
}
