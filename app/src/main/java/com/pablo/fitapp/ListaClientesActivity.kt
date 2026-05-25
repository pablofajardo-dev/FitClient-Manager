package com.pablo.fitapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ListaClientesActivity : AppCompatActivity() {

    private lateinit var dbHelper: ClienteDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_clientes)

        dbHelper = ClienteDbHelper(this)

        val tvClientes = findViewById<TextView>(R.id.tvClientes)
        val btnVolverLista = findViewById<Button>(R.id.btnVolverLista)

        val clientes = dbHelper.obtenerClientes()

        if (clientes.isEmpty()) {
            tvClientes.text = "No hay clientes registrados"
        } else {
            val textoClientes = StringBuilder()

            for (cliente in clientes) {
                textoClientes.append("ID: ${cliente.id}\n")
                textoClientes.append("Nombre: ${cliente.nombre} ${cliente.apellidos}\n")
                textoClientes.append("Teléfono: ${cliente.telefono}\n")
                textoClientes.append("Email: ${cliente.email}\n")
                textoClientes.append("Edad: ${cliente.edad}\n")
                textoClientes.append("Objetivo: ${cliente.objetivo}\n")
                textoClientes.append("Nivel: ${cliente.nivel}\n")
                textoClientes.append("Observaciones: ${cliente.observaciones}\n")
                textoClientes.append("-----------------------------\n\n")
            }

            tvClientes.text = textoClientes.toString()
        }

        btnVolverLista.setOnClickListener {
            finish()
        }
    }
}