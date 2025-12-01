// Ruta: ui/login/LoginScreen.kt
package com.example.login002v.ui.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel // Para pedir el ViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel() // Pedimos a ViewModel
) {
    val context = LocalContext.current // para poder mostrar Toasts

    // 'uiState' es la "libreta" para saber qué anotar
    val uiState = viewModel.uiState


    LaunchedEffect(key1 = uiState.isLoggedIn) {
        if (uiState.isLoggedIn) {
            // Si el viewModel nos dice que entramos, navegamos
            navController.navigate("HomeScreen/${uiState.username}") {
                popUpTo("LoginScreen") { inclusive = true } // Borra el login del historial
            }
        }
    }

    // Este espía revisa si 'registrationSuccess' cambia a 'true'
    LaunchedEffect(key1 = uiState.registrationSuccess) {
        if (uiState.registrationSuccess) {
            Toast.makeText(context, "¡Usuario registrado! Inicia sesión.", Toast.LENGTH_LONG).show()
            viewModel.onRegistrationSuccessHandled() // Avisa al jefe que ya mostramos el mensaje
        }
    }

    // El fondo verde
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
    ) {
        // La columna que centra todo
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // el título cambia si estamos en modo registro
            Text(
                text = if (uiState.isRegisterMode) "Registro" else "Bienvenido a Huerto Hogar",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF1B5E20),
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Campo de Email
            TextField(
                value = uiState.username,
                onValueChange = { viewModel.onUsernameChange(it) }, // Le avisa al jefe
                label = { Text("Usuario (email)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // Campo de Contraseña
            TextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChange(it) }, // Le avisa al jefe
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(), // Oculta la clave (•••)
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            // 'AnimatedVisibility' hace que aparezca y desaparezca con una animación
            AnimatedVisibility(visible = uiState.isRegisterMode) {
                TextField(
                    value = uiState.confirmPassword,
                    onValueChange = { viewModel.onConfirmPasswordChange(it) },
                    label = { Text("Confirmar Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
                )
            }

            // Muestra el mensaje de error si el jefe lo anotó en la libreta
            if (uiState.errorMessage != null) {
                Text(
                    text = uiState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // El botón principal
            Button(
                onClick = {
                    // Le avisamos al jefe qué botón se presionó
                    if (uiState.isRegisterMode) {
                        viewModel.onRegisterClick()
                    } else {
                        viewModel.onLoginClick()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (uiState.isRegisterMode) "Crear cuenta" else "Iniciar sesión")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // El botón de texto para cambiar de modo
            TextButton(onClick = { viewModel.onToggleMode() }) { // Le avisa al jefe
                Text(
                    text = if (uiState.isRegisterMode)
                        "¿Ya tienes cuenta? Inicia sesión"
                    else
                        "¿No tienes cuenta? Regístrate"
                )
            }
        }
    }
}

// El preview para el editor de Android Studio
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    LoginScreen(navController, viewModel())
}