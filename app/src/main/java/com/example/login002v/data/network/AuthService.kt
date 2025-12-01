package com.example.login002v.data.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

// Modelos para enviar o recibir datos del servidor
data class LoginRequest(val correo: String, val contrasena: String)
data class RegisterRequest(val correo: String, val contrasena: String, val rol: String = "usuario")
data class AuthResponse(val exito: Boolean, val mensaje: String, val usuario: UserData?)
data class UserData(val id: Int, val correo: String, val rol: String)

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse
}

object SpringRetrofit {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val api: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}