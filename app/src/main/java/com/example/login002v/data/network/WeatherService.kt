package com.example.login002v.data.network



import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//Modelo de datos
data class WeatherResponse(val current_weather: CurrentWeather)
data class CurrentWeather(val temperature: Double, val windspeed: Double)

//Interfaz de Retrofit
interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double,
        @Query("current_weather") current: Boolean = true
    ): WeatherResponse
}

//Objeto Singleton para usar la API
object WeatherRetrofit {
    private const val BASE_URL = "https://api.open-meteo.com/"

    val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}