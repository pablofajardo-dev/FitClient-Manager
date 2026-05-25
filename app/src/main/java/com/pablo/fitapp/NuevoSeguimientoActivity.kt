package com.pablo.fitapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NuevoSeguimientoActivity : AppCompatActivity() {

    private lateinit var dbHelper: ClienteDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_seguimiento)

        dbHelper = ClienteDbHelper(this)

        val etSeguimientoClienteId = findViewById<EditText>(R.id.etSeguimientoClienteId)
        val etSeguimientoFecha = findViewById<EditText>(R.id.etSeguimientoFecha)
        val etSeguimientoPeso = findViewById<EditText>(R.id.etSeguimientoPeso)
        val etSeguimientoObservaciones = findViewById<EditText>(R.id.etSeguimientoObservaciones)

        val btnGuardarSeguimiento = findViewById<Button>(R.id.btnGuardarSeguimiento)
        val btnVolverSeguimiento = findViewById<Button>(R.id.btnVolverSeguimiento)

        btnGuardarSeguimiento.setOnClickListener {
            val clienteIdTexto = etSeguimientoClienteId.text.toString().trim()
            val fecha = etSeguimientoFecha.text.toString().trim()
            val pesoTexto = etSeguimientoPeso.text.toString().trim()
            val observaciones = etSeguimientoObservaciones.text.toString().trim()

            if (clienteIdTexto.isEmpty()) {
                Toast.makeText(this, "El ID del cliente es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val clienteId = clienteIdTexto.toIntOrNull()

            if (clienteId == null) {
                Toast.makeText(this, "ID de cliente no válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cliente = dbHelper.obtenerClientePorId(clienteId)

            if (cliente == null) {
                Toast.makeText(this, "No existe ningún cliente con ese ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (fecha.isEmpty()) {
                Toast.makeText(this, "La fecha es obligatoria", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val peso = pesoTexto.replace(",", ".").toDoubleOrNull()

            if (peso == null) {
                Toast.makeText(this, "Introduce un peso válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val seguimiento = Seguimiento(
                clienteId = clienteId,
                fecha = fecha,
                peso = peso,
                observaciones = observaciones
            )

            val resultado = dbHelper.insertarSeguimiento(seguimiento)

            if (resultado != -1L) {
                Toast.makeText(this, "Seguimiento guardado correctamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar el seguimiento", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolverSeguimiento.setOnClickListener {
            finish()
        }
    }
}