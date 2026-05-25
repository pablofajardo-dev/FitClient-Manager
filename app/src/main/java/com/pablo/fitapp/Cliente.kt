package com.pablo.fitapp

data class Cliente(
    val id: Int = 0,
    val nombre: String,
    val apellidos: String,
    val telefono: String,
    val email: String,
    val edad: Int,
    val objetivo: String,
    val nivel: String,
    val observaciones: String
)