package com.pablo.fitapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ListaSeguimientosActivity : AppCompatActivity() {

    private lateinit var dbHelper: ClienteDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_seguimientos)

        dbHelper = ClienteDbHelper(this)

        val tvSeguimientos = findViewById<TextView>(R.id.tvSeguimientos)
        val btnVolverListaSeguimientos = findViewById<Button>(R.id.btnVolverListaSeguimientos)

        val seguimientos = dbHelper.obtenerSeguimientos()

        if (seguimientos.isEmpty()) {
            tvSeguimientos.text = "No hay seguimientos registrados"
        } else {
            val textoSeguimientos = StringBuilder()

            for (seguimiento in seguimientos) {
                val cliente = dbHelper.obtenerClientePorId(seguimiento.clienteId)
                val nombreCliente = if (cliente != null) {
                    "${cliente.nombre} ${cliente.apellidos}"
                } else {
                    "Cliente no encontrado"
                }

                textoSeguimientos.append("ID seguimiento: ${seguimiento.id}\n")
                textoSeguimientos.append("Cliente: $nombreCliente\n")
                textoSeguimientos.append("ID cliente: ${seguimiento.clienteId}\n")
                textoSeguimientos.append("Fecha: ${seguimiento.fecha}\n")
                textoSeguimientos.append("Peso: ${seguimiento.peso} kg\n")
                textoSeguimientos.append("Observaciones: ${seguimiento.observaciones}\n")
                textoSeguimientos.append("-----------------------------\n\n")
            }

            tvSeguimientos.text = textoSeguimientos.toString()
        }

        btnVolverListaSeguimientos.setOnClickListener {
            finish()
        }
    }
}