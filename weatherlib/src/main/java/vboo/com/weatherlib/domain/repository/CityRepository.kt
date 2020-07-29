package vboo.com.weatherlib.domain.repository

import kotlinx.coroutines.flow.Flow
import vboo.com.weatherlib.data.network.response.GetWeatherCityResponse
import vboo.com.weatherlib.domain.Result
import vboo.com.weatherlib.domain.model.City
import vboo.com.weatherlib.domain.usecases.CurrentCityWeatherResult

interface CityRepository {
    fun getListCity(): List<City>
    fun getCurrentCityWeather(idCity: Int): Flow<Result<CurrentCityWeatherResult>>
}