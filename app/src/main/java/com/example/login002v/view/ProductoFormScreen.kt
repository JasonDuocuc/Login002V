package com.example.login002v.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext // Importante para el Toast
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.login002v.R
import com.example.login002v.data.model.Producto
import com.example.login002v.viewmodel.ProductoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    navController: NavController,
    username: String,
    nombre: String,
    precio: String
) {
    // Descuento duoc
    val context = LocalContext.current
    val isDuocUser = username.lowercase().endsWith("@duocuc.cl")

    var cantidad by remember { mutableStateOf(TextFieldValue("")) }
    var direccion by remember { mutableStateOf(TextFieldValue("")) }
    var conCajonMadera by remember { mutableStateOf(false) }
    // Escuento por defecto
    var descuentoDuoc by remember { mutableStateOf(isDuocUser) }

    val viewModel: ProductoViewModel = viewModel()
    val productos: List<Producto> by viewModel.productos.collectAsState()

    val imagenId = when (nombre) {
        "Manzanas Fuji" -> R.drawable.manzana
        "Naranjas Valencia" -> R.drawable.naranja
        "Plátanos Cavendish" -> R.drawable.platano
        "Zanahorias Orgánicas" -> R.drawable.zanahoria
        "Espinacas Frescas" -> R.drawable.espinaca
        "Pimientos Tricolores" -> R.drawable.pimientos
        "Miel Orgánica" -> R.drawable.miel
        "Quinua Orgánica" -> R.drawable.quinoa
        "Leche Entera" -> R.drawable.leche
        else -> R.drawable.placeholder_huerto
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(nombre) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1B5E20), titleContentColor = Color.White, navigationIconContentColor = Color.White)
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // foto, título, formulario
                item { Image(painter = painterResource(id = imagenId), contentDescription = nombre, modifier = Modifier.height(170.dp).fillMaxWidth(), contentScale = ContentScale.Crop) }
                item { Spacer(modifier = Modifier.height(16.dp)) }
                item {
                    Text(text = nombre, style = MaterialTheme.typography.headlineSmall)
                    Text(text = "Precio: $$precio CLP", style = MaterialTheme.typography.bodyLarge)
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    OutlinedTextField(
                        value = cantidad,
                        onValueChange = { newValue ->
                            if (newValue.text.all { it.isDigit() }) {
                                cantidad = newValue
                            }
                        },
                        label = { Text("Cantidad") },
                        modifier = Modifier.fillMaxWidth(),
                        // Muestra el teclado numérico
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                item { Spacer(modifier = Modifier.height(8.dp)) }
                item { OutlinedTextField(value = direccion, onValueChange = { direccion = it }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth()) }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Checkbox(checked = conCajonMadera, onCheckedChange = { conCajonMadera = it })
                        Text("Incluir cajón de madera")
                    }
                }

                //checkbox de descuento duoc
                item {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        Checkbox(
                            checked = descuentoDuoc,
                            onCheckedChange = { isChecked ->
                                if (isChecked && !isDuocUser) {
                                    // Si intenta marcarlo y no es Duoc, muestra mensaje
                                    Toast.makeText(context, "Solo para alumnos Duoc", Toast.LENGTH_SHORT).show()
                                } else {
                                    // Permite el cambio (marcar si es Duoc, o desmarcar)
                                    descuentoDuoc = isChecked
                                }
                            }
                        )
                        Text("Descuento Cliente Duoc")
                    }
                }


                item { Spacer(modifier = Modifier.height(16.dp)) }

                item {
                    Button(
                        onClick = {
                            val producto = Producto(
                                nombre = nombre,
                                precio = precio,
                                cantidad = cantidad.text,
                                direccion = direccion.text,
                                conCajonMadera = conCajonMadera,
                                descuentoDuoc = descuentoDuoc
                            )
                            viewModel.guardarProducto(producto)

                            // Lleva a la pantalla de exito
                            navController.navigate("OrderSuccessScreen/$username") {
                                // Elimina esta pantalla del historial
                                popUpTo("ProductoFormScreen/$username/$nombre/$precio") { inclusive = true }
                            }
                        },
                        // La validación existente 'isNotBlank' funciona perfecto
                        enabled = cantidad.text.isNotBlank() && direccion.text.isNotBlank(),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Confirmar Pedido")
                    }
                }


                item { Spacer(modifier = Modifier.height(16.dp)) }
                item { Text("Pedidos realizados: ", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.fillMaxWidth()) }
                if (productos.isNotEmpty()) {
                    items(productos) { producto ->
                        Card(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(text = "${producto.nombre} - $${producto.precio} CLP", style = MaterialTheme.typography.bodyLarge)
                                Text(text = "Cantidad: ${producto.cantidad}", style = MaterialTheme.typography.bodyMedium)
                                Text(text = "Direccion: ${producto.direccion}", style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                } else {
                    item { Text("No hay pedidos realizados", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 8.dp)) }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductoFormScreen() {
    ProductoFormScreen(
        navController = rememberNavController(),
        username = "alumno@duocuc.cl",
        nombre = "Plátanos Cavendish",
        precio = "800"
    )
}