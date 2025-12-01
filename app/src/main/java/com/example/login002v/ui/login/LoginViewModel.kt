// Ruta: ui/login/LoginViewModel.kt
package com.example.login002v.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope // Necesario para procesos en segundo plano
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.login002v.data.repository.AuthRepository
import android.util.Patterns
import kotlinx.coroutines.launch // Necesario para lanzar las corrutinas

//maneja toda la lógica.
class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun onUsernameChange(newValue: String) {
        uiState = uiState.copy(username = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState = uiState.copy(password = newValue)
    }

    fun onConfirmPasswordChange(newValue: String) {
        uiState = uiState.copy(confirmPassword = newValue)
    }

    // para cambiar entre el modo Login y el modo Registro
    fun onToggleMode() {
        uiState = uiState.copy(
            isRegisterMode = !uiState.isRegisterMode,
            errorMessage = null,
            registrationSuccess = false
        )
    }

    // para limpiar el mensaje de éxito
    fun onRegistrationSuccessHandled() {
        uiState = uiState.copy(registrationSuccess = false)
    }

    // lógica cuando se presiona "Entrar"
    fun onLoginClick() {
        // Usamos 'launch' para que la app no se pegue mientras esperamos respuesta
        viewModelScope.launch {
            val success = repository.login(uiState.username, uiState.password)
            uiState = if (success) {
                uiState.copy(isLoggedIn = true, errorMessage = null)
            } else {
                uiState.copy(errorMessage = "Usuario o contraseña incorrectos", isLoggedIn = false)
            }
        }
    }

    // lógica cuando se presiona "Crear Cuenta"
    fun onRegisterClick() {

        // 1. Revisa si el email es válido
        if (!Patterns.EMAIL_ADDRESS.matcher(uiState.username).matches()) {
            uiState = uiState.copy(errorMessage = "Por favor, ingresa un email válido")
            return
        }

        // 2. Revisa si las claves coinciden
        if (uiState.password != uiState.confirmPassword) {
            uiState = uiState.copy(errorMessage = "Las contraseñas no coinciden")
            return
        }

        // 3. Revisa que no esté vacío
        if (uiState.username.isBlank() || uiState.password.isBlank()) {
            uiState = uiState.copy(errorMessage = "Email y contraseña no pueden estar vacíos")
            return
        }

        // Llamamos al registro en segundo plano
        viewModelScope.launch {
            val success = repository.register(uiState.username, uiState.password)

            uiState = if (success) {
                uiState.copy(registrationSuccess = true, errorMessage = null, isRegisterMode = false)
            } else {
                uiState.copy(errorMessage = "El email ya está registrado o hubo un error")
            }
        }
    }
}