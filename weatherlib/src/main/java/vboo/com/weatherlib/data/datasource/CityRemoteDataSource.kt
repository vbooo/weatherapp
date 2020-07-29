package vboo.com.weatherlib.data.datasource

import vboo.com.weatherlib.data.network.AppService
import vboo.com.weatherlib.data.network.response.GetWeatherCityResponse
import javax.inject.Inject

/**
 * This class handles remote data relative to City
 */
class CityRemoteDataSource @Inject constructor(private val appService: AppService) {

    suspend fun getCurrentCityWeather(cityId: Int): GetWeatherCityResponse {
        return appService.getCurrentCityWeather(cityId)
    }
}