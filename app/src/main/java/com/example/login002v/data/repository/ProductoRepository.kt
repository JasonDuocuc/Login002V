package com.example.login002v.data.repository

import com.example.login002v.data.dao.ProductoDao
import com.example.login002v.data.model.Producto
import kotlinx.coroutines.flow.Flow


class ProductoRepository (private val productoDao: ProductoDao){

suspend fun insertarProducto(producto: Producto){
        productoDao.insertarProducto(producto)
}

    fun obtenerProductos(): Flow<List<Producto>>{
        return productoDao.obtenerProductos()
    }

}// fin class
