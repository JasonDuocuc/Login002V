// Ruta: view/ProductListScreen.kt
package com.example.login002v.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login002v.data.model.productList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    navController: NavController,
    username: String,
    categoryName: String
) {

    // Filtra la lista de productos
    val filteredList = productList.filter { it.categoria == categoryName }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(categoryName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B5E20), // Verde oscuro
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8F5E9)) // Fondo verde claro
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredList) { producto ->
                    // Re-utilizamos el ProductoCard de MainMenuScreen
                    ProductoCard(
                        producto = producto,
                        navController = navController,
                        username = username, // <-- Pasa el username
                        showStock = true // <-- Pasa el showStock
                    )
                }
            }
        }
    }
}