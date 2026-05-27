package com.pablo.fitapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Spinner

class EditarEliminarClienteActivity : AppCompatActivity() {

    private lateinit var dbHelper: ClienteDbHelper
    private var clienteActualId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_eliminar_cliente)

        dbHelper = ClienteDbHelper(this)

        val etBuscarId = findViewById<EditText>(R.id.etBuscarId)

        val etNombre = findViewById<EditText>(R.id.etNombreEditar)
        val etApellidos = findViewById<EditText>(R.id.etApellidosEditar)
        val etTelefono = findViewById<EditText>(R.id.etTelefonoEditar)
        val etEmail = findViewById<EditText>(R.id.etEmailEditar)
        val etEdad = findViewById<EditText>(R.id.etEdadEditar)
        val etObjetivo = findViewById<EditText>(R.id.etObjetivoEditar)
        val spEditarNivel = findViewById<Spinner>(R.id.spEditarNivel)

        val niveles = arrayOf("Principiante", "Intermedio", "Avanzado")
        val adapterNivel = ArrayAdapter(this, android.R.layout.simple_spinner_item, niveles)
        adapterNivel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spEditarNivel.adapter = adapterNivel
        val etObservaciones = findViewById<EditText>(R.id.etObservacionesEditar)

        val btnBuscarCliente = findViewById<Button>(R.id.btnBuscarCliente)
        val btnActualizarCliente = findViewById<Button>(R.id.btnActualizarCliente)
        val btnEliminarCliente = findViewById<Button>(R.id.btnEliminarCliente)
        val btnVolverEditar = findViewById<Button>(R.id.btnVolverEditar)

        btnBuscarCliente.setOnClickListener {
            val idTexto = etBuscarId.text.toString().trim()

            if (idTexto.isEmpty()) {
                Toast.makeText(this, "Introduce un ID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idBuscado = idTexto.toIntOrNull()

            if (idBuscado == null) {
                Toast.makeText(this, "ID no válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cliente = dbHelper.obtenerClientePorId(idBuscado)

            if (cliente == null) {
                Toast.makeText(this, "No existe ningún cliente con ese ID", Toast.LENGTH_SHORT).show()
                clienteActualId = -1
            } else {
                clienteActualId = cliente.id

                etNombre.setText(cliente.nombre)
                etApellidos.setText(cliente.apellidos)
                etTelefono.setText(cliente.telefono)
                etEmail.setText(cliente.email)
                etEdad.setText(cliente.edad.toString())
                etObjetivo.setText(cliente.objetivo)

                val posicionNivel = niveles.indexOf(cliente.nivel)
                if (posicionNivel >= 0) {
                    spEditarNivel.setSelection(posicionNivel)
                } else {
                    spEditarNivel.setSelection(0)
                }

                etObservaciones.setText(cliente.observaciones)

                Toast.makeText(this, "Cliente cargado", Toast.LENGTH_SHORT).show()
            }
        }

        btnActualizarCliente.setOnClickListener {
            if (clienteActualId == -1) {
                Toast.makeText(this, "Primero busca un cliente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nombre = etNombre.text.toString().trim()
            val apellidos = etApellidos.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val edadTexto = etEdad.text.toString().trim()
            val objetivo = etObjetivo.text.toString().trim()
            val nivel = spEditarNivel.selectedItem.toString()
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

            val clienteActualizado = Cliente(
                id = clienteActualId,
                nombre = nombre,
                apellidos = apellidos,
                telefono = telefono,
                email = email,
                edad = edad,
                objetivo = objetivo,
                nivel = nivel,
                observaciones = observaciones
            )

            val filasActualizadas = dbHelper.actualizarCliente(clienteActualizado)

            if (filasActualizadas > 0) {
                Toast.makeText(this, "Cliente actualizado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "No se pudo actualizar el cliente", Toast.LENGTH_SHORT).show()
            }
        }

        btnEliminarCliente.setOnClickListener {
            if (clienteActualId == -1) {
                Toast.makeText(this, "Primero busca un cliente", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val filasEliminadas = dbHelper.eliminarCliente(clienteActualId)

            if (filasEliminadas > 0) {
                Toast.makeText(this, "Cliente eliminado correctamente", Toast.LENGTH_SHORT).show()

                clienteActualId = -1
                etBuscarId.text.clear()
                etNombre.text.clear()
                etApellidos.text.clear()
                etTelefono.text.clear()
                etEmail.text.clear()
                etEdad.text.clear()
                etObjetivo.text.clear()
                spEditarNivel.setSelection(0)
                etObservaciones.text.clear()
            } else {
                Toast.makeText(this, "No se pudo eliminar el cliente", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolverEditar.setOnClickListener {
            finish()
        }
    }
}