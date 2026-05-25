package com.pablo.fitapp

data class Rutina(
    val id: Int = 0,
    val clienteId: Int,
    val nombre: String,
    val descripcion: String,
    val diasSemana: String,
    val fechaInicio: String
)