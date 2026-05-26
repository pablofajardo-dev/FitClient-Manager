package com.pablo.fitapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SeguimientosPorClienteActivity : AppCompatActivity() {

    private lateinit var dbHelper: ClienteDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seguimientos_por_cliente)

        dbHelper = ClienteDbHelper(this)

        val etClienteIdSeguimientos = findViewById<EditText>(R.id.etClienteIdSeguimientos)
        val btnBuscarSeguimientosCliente = findViewById<Button>(R.id.btnBuscarSeguimientosCliente)
        val tvResultadoSeguimientosCliente = findViewById<TextView>(R.id.tvResultadoSeguimientosCliente)
        val btnVolverSeguimientosCliente = findViewById<Button>(R.id.btnVolverSeguimientosCliente)

        btnBuscarSeguimientosCliente.setOnClickListener {
            val idTexto = etClienteIdSeguimientos.text.toString().trim()

            if (idTexto.isEmpty()) {
                Toast.makeText(this, "Introduce el ID del cliente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val clienteId = idTexto.toIntOrNull()

            if (clienteId == null) {
                Toast.makeText(this, "ID de cliente no válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cliente = dbHelper.obtenerClientePorId(clienteId)

            if (cliente == null) {
                tvResultadoSeguimientosCliente.text = "No existe ningún cliente con ese ID."
                Toast.makeText(this, "Cliente no encontrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val seguimientos = dbHelper.obtenerSeguimientosPorCliente(clienteId)

            if (seguimientos.isEmpty()) {
                tvResultadoSeguimientosCliente.text =
                    "Cliente: ${cliente.nombre} ${cliente.apellidos}\n\nNo tiene seguimientos registrados."
            } else {
                val texto = StringBuilder()

                texto.append("Cliente: ${cliente.nombre} ${cliente.apellidos}\n")
                texto.append("ID cliente: ${cliente.id}\n")
                texto.append("-----------------------------\n\n")

                for (seguimiento in seguimientos) {
                    texto.append("ID seguimiento: ${seguimiento.id}\n")
                    texto.append("Fecha: ${seguimiento.fecha}\n")
                    texto.append("Peso: ${seguimiento.peso} kg\n")
                    texto.append("Observaciones: ${seguimiento.observaciones}\n")
                    texto.append("-----------------------------\n\n")
                }

                tvResultadoSeguimientosCliente.text = texto.toString()
            }
        }

        btnVolverSeguimientosCliente.setOnClickListener {
            finish()
        }
    }
}