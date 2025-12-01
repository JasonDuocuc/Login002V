// Ruta: view/AboutUsScreen.kt
package com.example.login002v.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sobre Nosotros") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B5E20),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8F5E9))
                .padding(innerPadding)
        ) {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                item {
                    InfoSection(title = "Sobre HuertoHogar", text = "Nuestra misión es proporcionar productos frescos y de calidad directamente desde el campo...")
                }
                item {
                    InfoSection(title = "Nuestra Misión", text = "Nuestra misión es proporcionar productos frescos y de calidad directamente desde el campo hasta la puerta de nuestros clientes...")
                }
                item {
                    InfoSection(title = "Nuestra Visión", text = "Nuestra visión es ser la tienda online líder en la distribución de productos frescos y naturales en Chile...")
                }
            }
        }
    }
}

@Composable
private fun InfoSection(title: String, text: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
        }
    }
}