package com.pablo.fitapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.CheckBox

class NuevaRutinaActivity : AppCompatActivity() {

    private lateinit var dbHelper: ClienteDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_rutina)

        dbHelper = ClienteDbHelper(this)

        val etRutinaClienteId = findViewById<EditText>(R.id.etRutinaClienteId)
        val etRutinaNombre = findViewById<EditText>(R.id.etRutinaNombre)
        val etRutinaDescripcion = findViewById<EditText>(R.id.etRutinaDescripcion)
        val cbLunes = findViewById<CheckBox>(R.id.cbLunes)
        val cbMartes = findViewById<CheckBox>(R.id.cbMartes)
        val cbMiercoles = findViewById<CheckBox>(R.id.cbMiercoles)
        val cbJueves = findViewById<CheckBox>(R.id.cbJueves)
        val cbViernes = findViewById<CheckBox>(R.id.cbViernes)
        val cbSabado = findViewById<CheckBox>(R.id.cbSabado)
        val cbDomingo = findViewById<CheckBox>(R.id.cbDomingo)
        val etRutinaFechaInicio = findViewById<EditText>(R.id.etRutinaFechaInicio)

        val btnGuardarRutina = findViewById<Button>(R.id.btnGuardarRutina)
        val btnVolverRutina = findViewById<Button>(R.id.btnVolverRutina)

        btnGuardarRutina.setOnClickListener {
            val clienteIdTexto = etRutinaClienteId.text.toString().trim()
            val nombre = etRutinaNombre.text.toString().trim()
            val descripcion = etRutinaDescripcion.text.toString().trim()
            val diasSeleccionados = mutableListOf<String>()

            if (cbLunes.isChecked) diasSeleccionados.add("Lunes")
            if (cbMartes.isChecked) diasSeleccionados.add("Martes")
            if (cbMiercoles.isChecked) diasSeleccionados.add("Miércoles")
            if (cbJueves.isChecked) diasSeleccionados.add("Jueves")
            if (cbViernes.isChecked) diasSeleccionados.add("Viernes")
            if (cbSabado.isChecked) diasSeleccionados.add("Sábado")
            if (cbDomingo.isChecked) diasSeleccionados.add("Domingo")

            if (diasSeleccionados.isEmpty()) {
                Toast.makeText(this, "Selecciona al menos un día de entrenamiento", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val diasSemana = diasSeleccionados.joinToString(", ")
            val fechaInicio = etRutinaFechaInicio.text.toString().trim()

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

            if (nombre.isEmpty()) {
                Toast.makeText(this, "El nombre de la rutina es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rutina = Rutina(
                clienteId = clienteId,
                nombre = nombre,
                descripcion = descripcion,
                diasSemana = diasSemana,
                fechaInicio = fechaInicio
            )

            val resultado = dbHelper.insertarRutina(rutina)

            if (resultado != -1L) {
                Toast.makeText(this, "Rutina guardada correctamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar la rutina", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolverRutina.setOnClickListener {
            finish()
        }
    }
}