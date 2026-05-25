package com.pablo.fitapp

data class Seguimiento(
    val id: Int = 0,
    val clienteId: Int,
    val fecha: String,
    val peso: Double,
    val observaciones: String
)