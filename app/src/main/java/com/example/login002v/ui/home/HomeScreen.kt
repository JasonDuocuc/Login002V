// Ruta: ui/home/HomeScreen.kt
package com.example.login002v.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavController, username: String) {

    LaunchedEffect(key1 = true) {
        delay(1000L) // Espera 1 segundo

        // Navega al MainMenuScreen
        navController.navigate("MainMenuScreen/$username") {
            // Elimina esta pantalla del historial.
            popUpTo("HomeScreen") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9)), // Fondo verde claro
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "🌿 Bienvenido, $username 🌿",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Cargando tu huerto...",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF1B5E20)
            )
        }
    }
}