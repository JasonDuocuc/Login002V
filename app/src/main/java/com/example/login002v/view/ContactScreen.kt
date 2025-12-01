// Ruta: view/ContactScreen.kt
package com.example.login002v.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacto") },
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
            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                item { InfoSection() }
                item { FormSection(nombre, {nombre=it}, correo, {correo=it}, asunto, {asunto=it}, mensaje, {mensaje=it}) }
            }
        }
    }
}

@Composable
private fun InfoSection() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Información de Contacto", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("Email: franciscoymanuel@huertohogar.cl")
            Text("Teléfono: +56 9 1234 5678")
            Text("Dirección: Av. concha y toro 1155, Santiago, Chile")
            Text("Síguenos: Facebook | Instagram | Twitter")
        }
    }
}

@Composable
private fun FormSection(
    nombre: String, onNombreChange: (String) -> Unit,
    correo: String, onCorreoChange: (String) -> Unit,
    asunto: String, onAsuntoChange: (String) -> Unit,
    mensaje: String, onMensajeChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("Escríbenos", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            OutlinedTextField(value = nombre, onValueChange = onNombreChange, label = { Text("Nombre") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = correo, onValueChange = onCorreoChange, label = { Text("Correo Electrónico") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = asunto, onValueChange = onAsuntoChange, label = { Text("Asunto") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = mensaje, onValueChange = onMensajeChange, label = { Text("Mensaje") }, modifier = Modifier.fillMaxWidth(), minLines = 4)
            Button(onClick = { /* Lógica de envío */ }, modifier = Modifier.fillMaxWidth()) {
                Text("Enviar Mensaje")
            }
        }
    }
}