package com.pablo.fitapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RutinasPorClienteActivity : AppCompatActivity() {

    private lateinit var dbHelper: ClienteDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutinas_por_cliente)

        dbHelper = ClienteDbHelper(this)

        val etClienteIdRutinas = findViewById<EditText>(R.id.etClienteIdRutinas)
        val btnBuscarRutinasCliente = findViewById<Button>(R.id.btnBuscarRutinasCliente)
        val tvResultadoRutinasCliente = findViewById<TextView>(R.id.tvResultadoRutinasCliente)
        val btnVolverRutinasCliente = findViewById<Button>(R.id.btnVolverRutinasCliente)

        btnBuscarRutinasCliente.setOnClickListener {
            val idTexto = etClienteIdRutinas.text.toString().trim()

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
                tvResultadoRutinasCliente.text = "No existe ningún cliente con ese ID."
                Toast.makeText(this, "Cliente no encontrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rutinas = dbHelper.obtenerRutinasPorCliente(clienteId)

            if (rutinas.isEmpty()) {
                tvResultadoRutinasCliente.text =
                    "Cliente: ${cliente.nombre} ${cliente.apellidos}\n\nNo tiene rutinas registradas."
            } else {
                val texto = StringBuilder()

                texto.append("Cliente: ${cliente.nombre} ${cliente.apellidos}\n")
                texto.append("ID cliente: ${cliente.id}\n")
                texto.append("-----------------------------\n\n")

                for (rutina in rutinas) {
                    texto.append("ID rutina: ${rutina.id}\n")
                    texto.append("Nombre: ${rutina.nombre}\n")
                    texto.append("Descripción: ${rutina.descripcion}\n")
                    texto.append("Días: ${rutina.diasSemana}\n")
                    texto.append("Fecha inicio: ${rutina.fechaInicio}\n")
                    texto.append("-----------------------------\n\n")
                }

                tvResultadoRutinasCliente.text = texto.toString()
            }
        }

        btnVolverRutinasCliente.setOnClickListener {
            finish()
        }
    }
}