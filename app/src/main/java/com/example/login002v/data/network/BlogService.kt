package com.example.login002v.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

//modelo de datos en Android
data class BlogTip(
    val id: Int = 0,
    val titulo: String,
    val contenido: String
)

//instrucciones para Retrofit
interface BlogApi {
    @GET("/api/blog")
    suspend fun getTips(): List<BlogTip>

    @POST("/api/blog")
    suspend fun createTip(@Body tip: BlogTip): BlogTip

    @PUT("/api/blog/{id}")
    suspend fun updateTip(@Path("id") id: Int, @Body tip: BlogTip): BlogTip

    @DELETE("/api/blog/{id}")
    suspend fun deleteTip(@Path("id") id: Int): Boolean
}

//objeto que hace la conexión
object BlogRetrofit {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val api: BlogApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BlogApi::class.java)
    }
}