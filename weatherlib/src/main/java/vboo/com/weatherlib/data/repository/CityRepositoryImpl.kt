package vboo.com.weatherlib.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vboo.com.weatherlib.data.NetworkUtils
import vboo.com.weatherlib.data.datasource.CityLocalDataSource
import vboo.com.weatherlib.data.datasource.CityRemoteDataSource
import vboo.com.weatherlib.data.db.HistoricWeatherCityEntity
import vboo.com.weatherlib.data.network.response.GetWeatherCityResponse
import vboo.com.weatherlib.domain.Result
import vboo.com.weatherlib.domain.model.City
import vboo.com.weatherlib.domain.repository.CityRepository
import vboo.com.weatherlib.domain.usecases.CurrentCityWeatherResult
import java.lang.Exception
import java.util.*
import javax.inject.Inject

/**
 * Implementation of [CityRepository]. It manipulates data related to
 */
class CityRepositoryImpl @Inject constructor(
    private val datasourceCityLocal: CityLocalDataSource,
    private val datasourceCityRemote: CityRemoteDataSource,
    private val networkUtils: NetworkUtils
): CityRepository
{
    companion object {
        // The openweathermap api cache is 10 min
        const val CACHE_MIN_API = 10
    }

    override fun getListCity(): List<City> {
        return datasourceCityLocal.getListCity()
    }

    fun mapToCurrentCityWeatherResult(data: Any?): CurrentCityWeatherResult? {

        return when (data) {
            is HistoricWeatherCityEntity -> {
                CurrentCityWeatherResult(
                    idCity = data.idCity,
                    name = data.name,
                    description = data.description,
                    icon = data.icon,
                    temp = data.temp,
                    temp_min = data.temp_min,
                    temp_max = data.temp_max,
                    humidity = data.humidity,
                    date = data.dateLastUpdate
                )
            }
            is GetWeatherCityResponse -> {
                CurrentCityWeatherResult(
                    idCity = data.id,
                    name = data.name,
                    description = data.weather?.first()?.description,
                    icon = data.weather?.first()?.icon,
                    temp = data.main?.temp,
                    temp_min = data.main?.temp_min,
                    temp_max = data.main?.temp_max,
                    humidity = data.main?.humidity,
                    date = Date()
                )
            }
            else -> null
        }

    }

    override fun getCurrentCityWeather(id: Int): Flow<Result<CurrentCityWeatherResult>> = flow {
        emit(Result.Loading)

        // We first try to get the information in local DB because API reloads data every 10 minutes
        // so it's useless to make an API call more than one time every 10 min
        val responseDB = datasourceCityLocal.getHistoricCity(id)

        val minutes: Int = responseDB?.let {
            val diff: Long = Date().time - it.dateLastUpdate.time
            diff.toInt() / 1000 / 60
        } ?: CACHE_MIN_API + 1

        // we return local data if api cache is still available or if we don't have internet connection
        // otherwise we return remote data and store them in local DB
        if (responseDB != null && minutes < CACHE_MIN_API || responseDB != null && !networkUtils.hasNetworkConnection()) {
            emit(Result.Success(data = mapToCurrentCityWeatherResult(responseDB)))
        } else if (networkUtils.hasNetworkConnection()) {

            try {
                // we emit the fresh data
                val response = datasourceCityRemote.getCurrentCityWeather(id)
                emit(Result.Success(data = mapToCurrentCityWeatherResult(response)))

                // we delete the old historic data
                datasourceCityLocal.deleteHistoricData(response.id!!)

                // and we save the data in the local DB
                datasourceCityLocal.saveHistoricCity(mapToCurrentCityWeatherResult(response))

            } catch (e: Exception) {
                emit(Result.Error(exception = e))
            }

        } else {
            emit(Result.Error(exception = null))
        }

    }

    override fun updateCityAsFavourite(city: City): City {
        return datasourceCityLocal.updateCityAsFavourite(city)
    }

}