package hu.simplexion.weather.service

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("/v1/current.json")
    suspend fun getWeatherInfo(
        @Query("q") q: String,
        @Query("lang") lang: String,
        @Query("key") key: String
    ): WeatherInfo
}