package com.pablo.fitapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Spinner

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
        val spNivel = findViewById<Spinner>(R.id.spNivel)

        val niveles = arrayOf("Principiante", "Intermedio", "Avanzado")
        val adapterNivel = ArrayAdapter(this, android.R.layout.simple_spinner_item, niveles)
        adapterNivel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spNivel.adapter = adapterNivel
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
            val nivel = spNivel.selectedItem.toString()
            val observaciones = etObservaciones.text.toString().trim()

            if (nombre.isEmpty()) {
                Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (nombre.any { it.isDigit() }) {
                Toast.makeText(this, "El nombre no debe contener números", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (apellidos.any { it.isDigit() }) {
                Toast.makeText(this, "Los apellidos no deben contener números", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (telefono.isNotEmpty() && !telefono.matches(Regex("^[0-9]{9}$"))) {
                Toast.makeText(this, "Introduce un teléfono válido de 9 dígitos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Introduce un correo electrónico válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val edad = edadTexto.toIntOrNull()

            if (edad == null || edad <= 0 || edad > 120) {
                Toast.makeText(this, "Introduce una edad válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
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