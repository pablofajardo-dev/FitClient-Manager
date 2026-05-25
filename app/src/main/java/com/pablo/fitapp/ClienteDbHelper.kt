package com.pablo.fitapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ClienteDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "fitclient.db"
        private const val DATABASE_VERSION = 3

        const val TABLE_CLIENTES = "clientes"
        const val COLUMN_ID = "id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_APELLIDOS = "apellidos"
        const val COLUMN_TELEFONO = "telefono"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_EDAD = "edad"
        const val COLUMN_OBJETIVO = "objetivo"
        const val COLUMN_NIVEL = "nivel"
        const val COLUMN_OBSERVACIONES = "observaciones"

        const val TABLE_RUTINAS = "rutinas"
        const val COLUMN_RUTINA_ID = "id"
        const val COLUMN_RUTINA_CLIENTE_ID = "cliente_id"
        const val COLUMN_RUTINA_NOMBRE = "nombre"
        const val COLUMN_RUTINA_DESCRIPCION = "descripcion"
        const val COLUMN_RUTINA_DIAS = "dias_semana"
        const val COLUMN_RUTINA_FECHA_INICIO = "fecha_inicio"

        const val TABLE_SEGUIMIENTOS = "seguimientos"
        const val COLUMN_SEGUIMIENTO_ID = "id"
        const val COLUMN_SEGUIMIENTO_CLIENTE_ID = "cliente_id"
        const val COLUMN_SEGUIMIENTO_FECHA = "fecha"
        const val COLUMN_SEGUIMIENTO_PESO = "peso"
        const val COLUMN_SEGUIMIENTO_OBSERVACIONES = "observaciones"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val crearTablaClientes = """
            CREATE TABLE $TABLE_CLIENTES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT NOT NULL,
                $COLUMN_APELLIDOS TEXT,
                $COLUMN_TELEFONO TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_EDAD INTEGER,
                $COLUMN_OBJETIVO TEXT,
                $COLUMN_NIVEL TEXT,
                $COLUMN_OBSERVACIONES TEXT
            )
        """.trimIndent()

        val crearTablaRutinas = """
            CREATE TABLE $TABLE_RUTINAS (
                $COLUMN_RUTINA_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_RUTINA_CLIENTE_ID INTEGER NOT NULL,
                $COLUMN_RUTINA_NOMBRE TEXT NOT NULL,
                $COLUMN_RUTINA_DESCRIPCION TEXT,
                $COLUMN_RUTINA_DIAS TEXT,
                $COLUMN_RUTINA_FECHA_INICIO TEXT,
                FOREIGN KEY($COLUMN_RUTINA_CLIENTE_ID) REFERENCES $TABLE_CLIENTES($COLUMN_ID)
            )
        """.trimIndent()

        val crearTablaSeguimientos = """
            CREATE TABLE $TABLE_SEGUIMIENTOS (
                $COLUMN_SEGUIMIENTO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_SEGUIMIENTO_CLIENTE_ID INTEGER NOT NULL,
                $COLUMN_SEGUIMIENTO_FECHA TEXT NOT NULL,
                $COLUMN_SEGUIMIENTO_PESO REAL,
                $COLUMN_SEGUIMIENTO_OBSERVACIONES TEXT,
                FOREIGN KEY($COLUMN_SEGUIMIENTO_CLIENTE_ID) REFERENCES $TABLE_CLIENTES($COLUMN_ID)
            )
        """.trimIndent()

        db.execSQL(crearTablaClientes)
        db.execSQL(crearTablaRutinas)
        db.execSQL(crearTablaSeguimientos)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SEGUIMIENTOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RUTINAS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CLIENTES")
        onCreate(db)
    }

    fun insertarCliente(cliente: Cliente): Long {
        val db = writableDatabase

        val valores = ContentValues().apply {
            put(COLUMN_NOMBRE, cliente.nombre)
            put(COLUMN_APELLIDOS, cliente.apellidos)
            put(COLUMN_TELEFONO, cliente.telefono)
            put(COLUMN_EMAIL, cliente.email)
            put(COLUMN_EDAD, cliente.edad)
            put(COLUMN_OBJETIVO, cliente.objetivo)
            put(COLUMN_NIVEL, cliente.nivel)
            put(COLUMN_OBSERVACIONES, cliente.observaciones)
        }

        return db.insert(TABLE_CLIENTES, null, valores)
    }

    fun obtenerClientes(): List<Cliente> {
        val listaClientes = mutableListOf<Cliente>()
        val db = readableDatabase

        val cursor = db.query(
            TABLE_CLIENTES,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_NOMBRE ASC"
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val apellidos = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APELLIDOS))
                val telefono = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONO))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                val edad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EDAD))
                val objetivo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OBJETIVO))
                val nivel = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIVEL))
                val observaciones = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OBSERVACIONES))

                val cliente = Cliente(
                    id = id,
                    nombre = nombre,
                    apellidos = apellidos,
                    telefono = telefono,
                    email = email,
                    edad = edad,
                    objetivo = objetivo,
                    nivel = nivel,
                    observaciones = observaciones
                )

                listaClientes.add(cliente)

            } while (cursor.moveToNext())
        }

        cursor.close()
        return listaClientes
    }

    fun obtenerClientePorId(idCliente: Int): Cliente? {
        val db = readableDatabase

        val cursor = db.query(
            TABLE_CLIENTES,
            null,
            "$COLUMN_ID = ?",
            arrayOf(idCliente.toString()),
            null,
            null,
            null
        )

        var cliente: Cliente? = null

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
            val apellidos = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APELLIDOS))
            val telefono = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TELEFONO))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val edad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EDAD))
            val objetivo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OBJETIVO))
            val nivel = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NIVEL))
            val observaciones = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_OBSERVACIONES))

            cliente = Cliente(
                id = id,
                nombre = nombre,
                apellidos = apellidos,
                telefono = telefono,
                email = email,
                edad = edad,
                objetivo = objetivo,
                nivel = nivel,
                observaciones = observaciones
            )
        }

        cursor.close()
        return cliente
    }

    fun actualizarCliente(cliente: Cliente): Int {
        val db = writableDatabase

        val valores = ContentValues().apply {
            put(COLUMN_NOMBRE, cliente.nombre)
            put(COLUMN_APELLIDOS, cliente.apellidos)
            put(COLUMN_TELEFONO, cliente.telefono)
            put(COLUMN_EMAIL, cliente.email)
            put(COLUMN_EDAD, cliente.edad)
            put(COLUMN_OBJETIVO, cliente.objetivo)
            put(COLUMN_NIVEL, cliente.nivel)
            put(COLUMN_OBSERVACIONES, cliente.observaciones)
        }

        return db.update(
            TABLE_CLIENTES,
            valores,
            "$COLUMN_ID = ?",
            arrayOf(cliente.id.toString())
        )
    }

    fun eliminarCliente(idCliente: Int): Int {
        val db = writableDatabase

        return db.delete(
            TABLE_CLIENTES,
            "$COLUMN_ID = ?",
            arrayOf(idCliente.toString())
        )
    }

    fun insertarRutina(rutina: Rutina): Long {
        val db = writableDatabase

        val valores = ContentValues().apply {
            put(COLUMN_RUTINA_CLIENTE_ID, rutina.clienteId)
            put(COLUMN_RUTINA_NOMBRE, rutina.nombre)
            put(COLUMN_RUTINA_DESCRIPCION, rutina.descripcion)
            put(COLUMN_RUTINA_DIAS, rutina.diasSemana)
            put(COLUMN_RUTINA_FECHA_INICIO, rutina.fechaInicio)
        }

        return db.insert(TABLE_RUTINAS, null, valores)
    }

    fun obtenerRutinas(): List<Rutina> {
        val listaRutinas = mutableListOf<Rutina>()
        val db = readableDatabase

        val cursor = db.query(
            TABLE_RUTINAS,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_RUTINA_ID DESC"
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RUTINA_ID))
                val clienteId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RUTINA_CLIENTE_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUTINA_NOMBRE))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUTINA_DESCRIPCION))
                val diasSemana = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUTINA_DIAS))
                val fechaInicio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUTINA_FECHA_INICIO))

                val rutina = Rutina(
                    id = id,
                    clienteId = clienteId,
                    nombre = nombre,
                    descripcion = descripcion,
                    diasSemana = diasSemana,
                    fechaInicio = fechaInicio
                )

                listaRutinas.add(rutina)

            } while (cursor.moveToNext())
        }

        cursor.close()
        return listaRutinas
    }

    fun obtenerRutinasPorCliente(clienteIdBuscado: Int): List<Rutina> {
        val listaRutinas = mutableListOf<Rutina>()
        val db = readableDatabase

        val cursor = db.query(
            TABLE_RUTINAS,
            null,
            "$COLUMN_RUTINA_CLIENTE_ID = ?",
            arrayOf(clienteIdBuscado.toString()),
            null,
            null,
            "$COLUMN_RUTINA_ID DESC"
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RUTINA_ID))
                val clienteId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RUTINA_CLIENTE_ID))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUTINA_NOMBRE))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUTINA_DESCRIPCION))
                val diasSemana = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUTINA_DIAS))
                val fechaInicio = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RUTINA_FECHA_INICIO))

                val rutina = Rutina(
                    id = id,
                    clienteId = clienteId,
                    nombre = nombre,
                    descripcion = descripcion,
                    diasSemana = diasSemana,
                    fechaInicio = fechaInicio
                )

                listaRutinas.add(rutina)

            } while (cursor.moveToNext())
        }

        cursor.close()
        return listaRutinas
    }

    fun insertarSeguimiento(seguimiento: Seguimiento): Long {
        val db = writableDatabase

        val valores = ContentValues().apply {
            put(COLUMN_SEGUIMIENTO_CLIENTE_ID, seguimiento.clienteId)
            put(COLUMN_SEGUIMIENTO_FECHA, seguimiento.fecha)
            put(COLUMN_SEGUIMIENTO_PESO, seguimiento.peso)
            put(COLUMN_SEGUIMIENTO_OBSERVACIONES, seguimiento.observaciones)
        }

        return db.insert(TABLE_SEGUIMIENTOS, null, valores)
    }

    fun obtenerSeguimientos(): List<Seguimiento> {
        val listaSeguimientos = mutableListOf<Seguimiento>()
        val db = readableDatabase

        val cursor = db.query(
            TABLE_SEGUIMIENTOS,
            null,
            null,
            null,
            null,
            null,
            "$COLUMN_SEGUIMIENTO_ID DESC"
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SEGUIMIENTO_ID))
                val clienteId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SEGUIMIENTO_CLIENTE_ID))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEGUIMIENTO_FECHA))
                val peso = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_SEGUIMIENTO_PESO))
                val observaciones = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEGUIMIENTO_OBSERVACIONES))

                val seguimiento = Seguimiento(
                    id = id,
                    clienteId = clienteId,
                    fecha = fecha,
                    peso = peso,
                    observaciones = observaciones
                )

                listaSeguimientos.add(seguimiento)

            } while (cursor.moveToNext())
        }

        cursor.close()
        return listaSeguimientos
    }

    fun obtenerSeguimientosPorCliente(clienteIdBuscado: Int): List<Seguimiento> {
        val listaSeguimientos = mutableListOf<Seguimiento>()
        val db = readableDatabase

        val cursor = db.query(
            TABLE_SEGUIMIENTOS,
            null,
            "$COLUMN_SEGUIMIENTO_CLIENTE_ID = ?",
            arrayOf(clienteIdBuscado.toString()),
            null,
            null,
            "$COLUMN_SEGUIMIENTO_ID DESC"
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SEGUIMIENTO_ID))
                val clienteId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SEGUIMIENTO_CLIENTE_ID))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEGUIMIENTO_FECHA))
                val peso = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_SEGUIMIENTO_PESO))
                val observaciones = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEGUIMIENTO_OBSERVACIONES))

                val seguimiento = Seguimiento(
                    id = id,
                    clienteId = clienteId,
                    fecha = fecha,
                    peso = peso,
                    observaciones = observaciones
                )

                listaSeguimientos.add(seguimiento)

            } while (cursor.moveToNext())
        }

        cursor.close()
        return listaSeguimientos
    }
}