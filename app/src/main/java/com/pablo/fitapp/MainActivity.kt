package com.pablo.fitapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnClientes = findViewById<Button>(R.id.btnClientes)
        val btnNuevoCliente = findViewById<Button>(R.id.btnNuevoCliente)
        val btnEditarEliminarCliente = findViewById<Button>(R.id.btnEditarEliminarCliente)
        val btnNuevaRutina = findViewById<Button>(R.id.btnNuevaRutina)
        val btnVerRutinas = findViewById<Button>(R.id.btnVerRutinas)
        val btnNuevoSeguimiento = findViewById<Button>(R.id.btnNuevoSeguimiento)
        val btnVerSeguimientos = findViewById<Button>(R.id.btnVerSeguimientos)

        btnClientes.setOnClickListener {
            val intent = Intent(this, ListaClientesActivity::class.java)
            startActivity(intent)
        }

        btnNuevoCliente.setOnClickListener {
            val intent = Intent(this, NuevoClienteActivity::class.java)
            startActivity(intent)
        }

        btnEditarEliminarCliente.setOnClickListener {
            val intent = Intent(this, EditarEliminarClienteActivity::class.java)
            startActivity(intent)
        }

        btnNuevaRutina.setOnClickListener {
            val intent = Intent(this, NuevaRutinaActivity::class.java)
            startActivity(intent)
        }

        btnVerRutinas.setOnClickListener {
            val intent = Intent(this, ListaRutinasActivity::class.java)
            startActivity(intent)
        }

        btnNuevoSeguimiento.setOnClickListener {
            val intent = Intent(this, NuevoSeguimientoActivity::class.java)
            startActivity(intent)
        }

        btnVerSeguimientos.setOnClickListener {
            val intent = Intent(this, ListaSeguimientosActivity::class.java)
            startActivity(intent)
        }
    }
}