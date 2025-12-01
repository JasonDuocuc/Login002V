package com.example.login002v.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

// Traemos todas las pantallas que creamos en la carpeta 'view'
import com.example.login002v.ui.home.HomeScreen
import com.example.login002v.ui.login.LoginScreen
import com.example.login002v.view.*

@Composable
fun AppNav(navController: NavHostController) {
    // Este NavHost es como el "mapa" de toda la app.
    // Le decimos que arranque en "LoginScreen".
    NavHost(navController = navController, startDestination = "LoginScreen") {

        // La pantalla inicial para entrar
        composable("LoginScreen") {
            LoginScreen(navController)
        }

        // La pantalla de bienvenida que dura 1 segundo
        composable(
            route = "HomeScreen/{username}", // La ruta necesita el nombre de usuario
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            // Aquí sacamos el username de la ruta
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
            HomeScreen(navController = navController, username = username)
        }

        // El menú principal con las categorías
        composable(
            route = "MainMenuScreen/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
            MainMenuScreen(navController = navController, username = username)
        }

        // La pantalla que muestra TODOS los productos
        composable(
            route = "DrawerMenu/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
            DrawerMenu(username = username, navController = navController)
        }

        // La pantalla para comprar un producto
        composable(
            // Esta ruta es más compleja, necesita 3 datos para funcionar:
            route = "ProductoFormScreen/{username}/{nombre}/{precio}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType }, // Para el descuento Duoc
                navArgument("nombre") { type = NavType.StringType },
                navArgument("precio") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Sacamos los 3 datos de la ruta
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val precio = backStackEntry.arguments?.getString("precio") ?: ""
            ProductoFormScreen(navController = navController, username = username, nombre = nombre, precio = precio)
        }


        // La pantalla que filtra productos por categoría
        composable(
            // Esta también necesita 2 datos: el usuario y el nombre de la categoría
            route = "ProductListScreen/{username}/{categoryName}",
            arguments = listOf(
                navArgument("username") { type = NavType.StringType },
                navArgument("categoryName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // Sacamos ambos argumentos
            val username = backStackEntry.arguments?.getString("username") ?: ""
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
            // Y se los pasamos a la pantalla
            ProductListScreen(navController = navController, username = username, categoryName = categoryName)
        }

        // La página "Sobre Nosotros"
        composable(route = "AboutUsScreen") {
            AboutUsScreen(navController = navController)
        }

        // La página del Blog con consejos
        composable(route = "BlogScreen") {
            BlogScreen(navController = navController)
        }

        // La página de Contacto con el formulario
        composable(route = "ContactScreen") {
            ContactScreen(navController = navController)
        }

        // La pantalla de "Gracias por su compra"
        composable(
            route = "OrderSuccessScreen/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
            OrderSuccessScreen(navController = navController, username = username)
        }
    }
}