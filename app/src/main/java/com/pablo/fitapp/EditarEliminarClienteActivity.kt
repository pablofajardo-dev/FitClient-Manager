package com.pablo.fitapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

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
        val etNivel = findViewById<EditText>(R.id.etNivelEditar)
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
                etNivel.setText(cliente.nivel)
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

            if (nombre.isEmpty()) {
                Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val edad = etEdad.text.toString().trim().toIntOrNull() ?: 0

            val clienteActualizado = Cliente(
                id = clienteActualId,
                nombre = nombre,
                apellidos = etApellidos.text.toString().trim(),
                telefono = etTelefono.text.toString().trim(),
                email = etEmail.text.toString().trim(),
                edad = edad,
                objetivo = etObjetivo.text.toString().trim(),
                nivel = etNivel.text.toString().trim(),
                observaciones = etObservaciones.text.toString().trim()
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
                etNivel.text.clear()
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