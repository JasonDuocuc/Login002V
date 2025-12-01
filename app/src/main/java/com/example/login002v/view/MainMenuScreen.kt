// Ruta: view/MainMenuScreen.kt
package com.example.login002v.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // íconos de Material 3
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.login002v.data.model.ProductoItem
import com.example.login002v.data.model.productList
import kotlinx.coroutines.launch
import kotlin.random.Random

// Un molde simple solo para este archivo, para organizar las categorías.
private data class Categoria(val titulo: String, val descripcion: String, val routeName: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    username: String,
    navController: NavController
) {
    // Tomamos 3 productos al azar de la lista.
    val randomProducts = remember { productList.shuffled().take(3) }

    // La lista de categorías que vamos a mostrar
    val categorias = listOf(
        Categoria("Frutas Frescas", "Nuestra selección de frutas...", "Frutas Frescas"),
        Categoria("Verduras Orgánicas", "Descubre nuestra gama de verduras...", "Verduras Orgánicas"),
        Categoria("Productos Orgánicos", "Nuestros productos orgánicos están...", "Productos Orgánicos"),
        Categoria("Lácteos", "Los productos lácteos de HuertoHogar...", "Lácteos")
    )

    //LÓGICA DEL CLIMA
    var clima by remember { mutableStateOf("Cargando clima...") }

    // Esto se ejecuta una sola vez al abrir la pantalla para pedir el clima
    LaunchedEffect(Unit) {
        try {
            // Llamamos a la API
            // Asegúrate de tener el archivo WeatherService.kt creado para que esto funcione
            val response = com.example.login002v.data.network.WeatherRetrofit.api.getCurrentWeather(-33.44, -70.66)
            clima = "${response.current_weather.temperature}°C - Viento: ${response.current_weather.windspeed} km/h"
        } catch (e: Exception) {
            clima = "Clima no disponible" // Si falla , mostramos esto
        }
    }
    // --------------------------------

    // Esto es para controlar el menú hamburguesa
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(12.dp))

                // Cada uno es un botón del menú
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.List, null) },
                    label = { Text("Todos los Productos") },
                    selected = false,
                    onClick = {
                        navController.navigate("DrawerMenu/$username")
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Info, null) },
                    label = { Text("Nosotros") },
                    selected = false,
                    onClick = {
                        navController.navigate("AboutUsScreen")
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Book, null) },
                    label = { Text("Blog") },
                    selected = false,
                    onClick = {
                        navController.navigate("BlogScreen")
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Default.Email, null) },
                    label = { Text("Contacto") },
                    selected = false,
                    onClick = {
                        navController.navigate("ContactScreen")
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Bienvenido, $username") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF1B5E20),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFE8F5E9))
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {

                    //TARJETA DEL CLIMA
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)), // Un azulito suave
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.WbSunny, contentDescription = null, tint = Color(0xFFF57F17)) // Sol naranja
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text("Clima en tu Huerto", style = MaterialTheme.typography.labelLarge, color = Color.Gray)
                                    Text(clima, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                    // Título de la primera sección
                    item {
                        Text("Nuestras Categorías", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20), modifier = Modifier.padding(bottom = 12.dp))
                    }
                    // Creamos una tarjeta por cada ítem en la lista 'categorias'
                    items(categorias) { categoria ->
                        CategoriaCard(categoria = categoria, onClick = {
                            navController.navigate("ProductListScreen/$username/${categoria.routeName}")
                        })
                    }
                    // Título de la segunda sección
                    item {
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text("Productos Destacados", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20), modifier = Modifier.weight(1f))
                            TextButton(onClick = { navController.navigate("DrawerMenu/$username") }) {
                                Text("Ver todo")
                            }
                        }
                    }
                    // Mostramos las 3 tarjetas de productos al azar
                    items(randomProducts) { producto ->
                        ProductoCard(
                            producto = producto,
                            navController = navController,
                            username = username,
                            showStock = false
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoriaCard(categoria: Categoria, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(categoria.titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text(categoria.descripcion, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray, maxLines = 2)
        }
    }
}


// Esta función es 'public' porque la usamos en OTROS archivos
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoCard(
    producto: ProductoItem,
    navController: NavController,
    username: String,
    showStock: Boolean = false
) {
    val precio = (producto.precioBase).toString()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        onClick = {
            val encodedName = Uri.encode(producto.nombre)
            navController.navigate("ProductoFormScreen/$username/$encodedName/$precio")
        },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = producto.drawableId),
                contentDescription = producto.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(producto.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("$$precio CLP / kg", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)

                if (showStock) {
                    Text("Stock: ${producto.stock} un.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuScreenPreview() {
    MainMenuScreen(username = "Francisco", navController = rememberNavController())
}