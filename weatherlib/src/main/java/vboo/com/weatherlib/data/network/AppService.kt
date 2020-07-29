package vboo.com.weatherlib.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import vboo.com.weatherlib.data.network.response.GetWeatherCityResponse

/**
 * Interface for handle API informations
 */
interface AppService {

    companion object {
        const val baseUrl = "https://api.openweathermap.org/data/2.5/"
        const val token = "6e3bc35608ba7889c77d41ed1dd358e9"
    }

    @GET("weather?lang=fr&appid=$token")
    suspend fun getCurrentCityWeather(@Query("id") id: Int): GetWeatherCityResponse
}