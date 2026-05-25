package com.pablo.fitapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NuevoClienteActivity : AppCompatActivity() {

    private lateinit var dbHelper: ClienteDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_cliente)

        dbHelper = ClienteDbHelper(this)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellidos = findViewById<EditText>(R.id.etApellidos)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etEdad = findViewById<EditText>(R.id.etEdad)
        val etObjetivo = findViewById<EditText>(R.id.etObjetivo)
        val etNivel = findViewById<EditText>(R.id.etNivel)
        val etObservaciones = findViewById<EditText>(R.id.etObservaciones)

        val btnGuardarCliente = findViewById<Button>(R.id.btnGuardarCliente)
        val btnVolver = findViewById<Button>(R.id.btnVolver)

        btnGuardarCliente.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val apellidos = etApellidos.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val edadTexto = etEdad.text.toString().trim()
            val objetivo = etObjetivo.text.toString().trim()
            val nivel = etNivel.text.toString().trim()
            val observaciones = etObservaciones.text.toString().trim()

            if (nombre.isEmpty()) {
                Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val edad = if (edadTexto.isNotEmpty()) {
                edadTexto.toIntOrNull() ?: 0
            } else {
                0
            }

            val cliente = Cliente(
                nombre = nombre,
                apellidos = apellidos,
                telefono = telefono,
                email = email,
                edad = edad,
                objetivo = objetivo,
                nivel = nivel,
                observaciones = observaciones
            )

            val resultado = dbHelper.insertarCliente(cliente)

            if (resultado != -1L) {
                Toast.makeText(this, "Cliente guardado correctamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Error al guardar el cliente", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolver.setOnClickListener {
            finish()
        }
    }
}