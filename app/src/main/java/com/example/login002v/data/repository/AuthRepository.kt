// Ruta: data/repository/AuthRepository.kt
package com.example.login002v.data.repository

import com.example.login002v.data.network.LoginRequest
import com.example.login002v.data.network.RegisterRequest
import com.example.login002v.data.network.SpringRetrofit

class AuthRepository {

    // Mantenemos tu base de datos local como respaldo
    private val userDatabase = mutableMapOf(
        "admin" to "123",
        "alumno@duocuc.cl" to "123"
    )

    // Esta función ahora es 'suspend' porque conectarse a internet toma tiempo
    suspend fun login(username: String, pass: String): Boolean {
        return try {
            // 1. Intentamos preguntar al servidor Spring Boot
            val response = SpringRetrofit.api.login(LoginRequest(username, pass))
            response.exito
        } catch (e: Exception) {
            // 2. Si el servidor falla, usamos el mapa local
            println("El servidor no responde, usando datos locales: ${e.message}")
            userDatabase[username.lowercase()] == pass
        }
    }

    // Lo mismo para el registro
    suspend fun register(username: String, pass: String): Boolean {
        return try {
            // 1. Intentamos registrar en el servidor
            val response = SpringRetrofit.api.register(RegisterRequest(username, pass))
            response.exito
        } catch (e: Exception) {
            // 2. Si falla la red, guardamos en local
            if (userDatabase.containsKey(username.lowercase())) {
                return false // Ya existe localmente
            }
            userDatabase[username.lowercase()] = pass
            true // Guardado en local con éxito
        }
    }
}