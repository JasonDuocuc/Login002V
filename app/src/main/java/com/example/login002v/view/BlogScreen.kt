// Ruta: view/BlogScreen.kt
package com.example.login002v.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login002v.data.network.BlogTip
import com.example.login002v.data.network.BlogRetrofit // Importamos nuestro servicio
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope() // Para tareas en segundo plano

    // Aquí guardamos la lista de consejos que llegan del servidor
    var tipsList by remember { mutableStateOf(listOf<BlogTip>()) }
    var isLoading by remember { mutableStateOf(true) }

    // Variables para controlar el cuadro de diálogo (popup)
    var showDialog by remember { mutableStateOf(false) }
    var currentTip by remember { mutableStateOf<BlogTip?>(null) } // null = nuevo, con datos = editar

    // Variables temporales para lo que escribes en el cuadro
    var tituloTemp by remember { mutableStateOf("") }
    var contenidoTemp by remember { mutableStateOf("") }

    // Función para pedir los datos al servidor Spring Boot
    fun loadTips() {
        scope.launch {
            try {
                tipsList = BlogRetrofit.api.getTips()
                isLoading = false
            } catch (e: Exception) {
                // Si falla mostramos error
                Toast.makeText(context, "Error de conexión: ${e.message}", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        }
    }

    //esto se ejecuta apenas se abre la pantalla
    LaunchedEffect(Unit) {
        loadTips()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Consejos de la Comunidad") },
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
        },
        //botón flotante (+) para agregar consejo
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    currentTip = null // Limpiamos para decir que es NUEVO
                    tituloTemp = ""
                    contenidoTemp = ""
                    showDialog = true // Mostramos el popup
                },
                containerColor = Color(0xFF1B5E20),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8F5E9))
                .padding(innerPadding)
        ) {
            if (isLoading) {
                // Mostramos un circulito cargando
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                // Lista de consejos
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(tipsList) { tip ->
                        BlogTipCard(
                            tip = tip,
                            onEdit = {
                                //preparamos los datos para editar
                                currentTip = tip
                                tituloTemp = tip.titulo
                                contenidoTemp = tip.contenido
                                showDialog = true
                            },
                            onDelete = {
                                // Borramos del servidor
                                scope.launch {
                                    try {
                                        BlogRetrofit.api.deleteTip(tip.id)
                                        loadTips() // Recargamos la lista
                                        Toast.makeText(context, "Consejo eliminado", Toast.LENGTH_SHORT).show()
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error al borrar", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        )
                    }
                    // Espacio extra al final
                    item { Spacer(modifier = Modifier.height(60.dp)) }
                }
            }
        }

        //CUADRO DE DIÁLOGO
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(if (currentTip == null) "Nuevo Consejo" else "Editar Consejo") },
                text = {
                    Column {
                        OutlinedTextField(
                            value = tituloTemp,
                            onValueChange = { tituloTemp = it },
                            label = { Text("Título (ej: Medir pH)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = contenidoTemp,
                            onValueChange = { contenidoTemp = it },
                            label = { Text("Contenido") },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (tituloTemp.isBlank() || contenidoTemp.isBlank()) {
                                Toast.makeText(context, "Completa los campos", Toast.LENGTH_SHORT).show()
                                return@TextButton
                            }

                            scope.launch {
                                try {
                                    if (currentTip == null) {
                                        //CREAR
                                        BlogRetrofit.api.createTip(BlogTip(titulo = tituloTemp, contenido = contenidoTemp))
                                    } else {
                                        //EDITAR
                                        BlogRetrofit.api.updateTip(
                                            currentTip!!.id,
                                            BlogTip(id = currentTip!!.id, titulo = tituloTemp, contenido = contenidoTemp)
                                        )
                                    }
                                    loadTips() //Recargar lista
                                    showDialog = false //Cerrar popup
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    ) {
                        Text("Guardar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

//Tarjeta individual para cada consejo
@Composable
private fun BlogTipCard(
    tip: BlogTip,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Título
                Text(
                    text = tip.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20),
                    modifier = Modifier.weight(1f)
                )
                // Botones para editar y borrar
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Gray)
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red)
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = tip.contenido,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
    }
}