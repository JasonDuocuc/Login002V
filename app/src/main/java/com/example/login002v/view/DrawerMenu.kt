package com.example.login002v.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Para usar .items en la LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.login002v.data.model.productList // Importa la lista de productos

@OptIn(ExperimentalMaterial3Api::class) // Necesario para el Scaffold y TopAppBar
@Composable
fun DrawerMenu(
    username: String, // Recibimos el username
    navController: NavController
) {
    // Usamos Scaffold para tener una estructura de pantalla con barra superior
    Scaffold(
        topBar = {
            // Esta es la barra verde de arriba
            TopAppBar(
                title = { Text("Todos los Productos") },
                // El ícono de la flecha para volver
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // 'popBackStack' es "volver atrás"
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                // Le damos los colores corporativos
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1B5E20), // Fondo verde oscuro
                    titleContentColor = Color.White,     // Texto blanco
                    navigationIconContentColor = Color.White // Flecha blanca
                )
            )
        }
    ) { innerPadding -> // 'innerPadding' es el espacio que deja la barra superior

        // Un Box para poner el fondo verde claro
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8F5E9))
                .padding(innerPadding) // Aplicamos el padding para no quedar debajo de la barra
        ) {
            // Usamos LazyColumn para que la lista sea eficiente y tenga scroll
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp), // Un poco de espacio en los bordes
                verticalArrangement = Arrangement.spacedBy(12.dp) // Espacio entre cada tarjeta
            ) {
                //crea un item por cada producto en nuestra lista
                items(productList) { producto ->
                    // Aquí re-usamos la 'ProductoCard' que definimos en MainMenuScreen
                    // Le pasamos toda la info que necesita.
                    ProductoCard(
                        producto = producto,
                        navController = navController,
                        username = username, // Se lo pasamos para la lógica del descuento
                        showStock = true     // Le decimos que SÍ muestre el stock
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DrawerMenuPreview() {
    val navController = rememberNavController()
    DrawerMenu(username = "Francisco", navController = navController)
}