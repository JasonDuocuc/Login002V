
package com.example.login002v.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun OrderSuccessScreen(navController: NavController, username: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9)), // Fondo verde claro
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Éxito",
                tint = Color(0xFF1B5E20),
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¡Gracias por su compra!",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Su pedido va en camino.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Color.DarkGray
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Vuelve al menú principal y limpia todo el historial
                    navController.navigate("MainMenuScreen/$username") {
                        popUpTo(0)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al Menú")
            }
        }
    }
}