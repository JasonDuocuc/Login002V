package com.example.login002v.data.model

import com.example.login002v.R

data class ProductoItem(
    val nombre: String,
    val drawableId: Int,
    val precioBase: Int,
    val stock: Int,
    val categoria: String
)

// Lista de productos con listOF}
val productList = listOf(
    // Frutas Frescas
    ProductoItem("Manzanas Fuji", R.drawable.manzana, 1200, 150, "Frutas Frescas"),
    ProductoItem("Naranjas Valencia", R.drawable.naranja, 1000, 200, "Frutas Frescas"),
    ProductoItem("Plátanos Cavendish", R.drawable.platano, 800, 250, "Frutas Frescas"),

    // Verduras Orgánicas
    ProductoItem("Zanahorias Orgánicas", R.drawable.zanahoria, 900, 100, "Verduras Orgánicas"),
    ProductoItem("Espinacas Frescas", R.drawable.espinaca, 700, 80, "Verduras Orgánicas"),
    ProductoItem("Pimientos Tricolores", R.drawable.pimientos, 1500, 120, "Verduras Orgánicas"), // <-- CORREGIDO

    // Productos Orgánicos
    ProductoItem("Miel Orgánica", R.drawable.miel, 5000, 50, "Productos Orgánicos"), // <-- CORREGIDO
    ProductoItem("Quinua Orgánica", R.drawable.quinoa, 3500, 60, "Productos Orgánicos"),

    // Lácteos
    ProductoItem("Leche Entera", R.drawable.leche, 1200, 200, "Lácteos") // <-- CORREGIDO
)