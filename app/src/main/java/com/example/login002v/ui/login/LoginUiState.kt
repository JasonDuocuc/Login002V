package com.example.login002v.ui.login
// esta es la memoria del ViewModel.
// solo guarda los datos de la pantalla.

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "", // Para el campo de confirmar
    val isLoggedIn: Boolean = false,
    val errorMessage: String? = null,
    val isRegisterMode: Boolean = false, // para saber si estamos en Login o registro
    val registrationSuccess: Boolean = false // Para avisar cuando el registro funciona
)