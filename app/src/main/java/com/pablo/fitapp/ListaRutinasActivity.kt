package com.pablo.fitapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ListaRutinasActivity : AppCompatActivity() {

    private lateinit var dbHelper: ClienteDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_rutinas)

        dbHelper = ClienteDbHelper(this)

        val tvRutinas = findViewById<TextView>(R.id.tvRutinas)
        val btnVolverListaRutinas = findViewById<Button>(R.id.btnVolverListaRutinas)

        val rutinas = dbHelper.obtenerRutinas()

        if (rutinas.isEmpty()) {
            tvRutinas.text = "No hay rutinas registradas"
        } else {
            val textoRutinas = StringBuilder()

            for (rutina in rutinas) {
                val cliente = dbHelper.obtenerClientePorId(rutina.clienteId)
                val nombreCliente = if (cliente != null) {
                    "${cliente.nombre} ${cliente.apellidos}"
                } else {
                    "Cliente no encontrado"
                }

                textoRutinas.append("ID rutina: ${rutina.id}\n")
                textoRutinas.append("Cliente: $nombreCliente\n")
                textoRutinas.append("ID cliente: ${rutina.clienteId}\n")
                textoRutinas.append("Nombre: ${rutina.nombre}\n")
                textoRutinas.append("Descripción: ${rutina.descripcion}\n")
                textoRutinas.append("Días: ${rutina.diasSemana}\n")
                textoRutinas.append("Fecha inicio: ${rutina.fechaInicio}\n")
                textoRutinas.append("-----------------------------\n\n")
            }

            tvRutinas.text = textoRutinas.toString()
        }

        btnVolverListaRutinas.setOnClickListener {
            finish()
        }
    }
}