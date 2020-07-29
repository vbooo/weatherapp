package vboo.com.weatherlib.data.datasource

import vboo.com.weatherlib.data.db.AppDatabase
import vboo.com.weatherlib.data.db.CityEntityDataMapper
import vboo.com.weatherlib.data.db.HistoricWeatherCityEntity
import vboo.com.weatherlib.domain.model.City
import vboo.com.weatherlib.domain.usecases.CurrentCityWeatherResult
import java.util.*
import javax.inject.Inject

/**
 * This class handles local data relative to City
 */
class CityLocalDataSource @Inject constructor(
    private val appDatabase: AppDatabase,
    private val mapper: CityEntityDataMapper
)
{
    fun getListCity(): List<City> {
        val listCityEntity = appDatabase.cityDao().getAll()
        val listCity = mutableListOf<City>()

        for (city in listCityEntity) {
            listCity.add(mapper.transform(city))
        }

        return listCity
    }

    fun deleteHistoricData(id: Int) {
        appDatabase.historicWeatherCityDao().deleteByIdCity(id)
    }

    fun saveHistoricCity(city: CurrentCityWeatherResult?) {
        val historicCityEntity = HistoricWeatherCityEntity(
            idCity = city?.idCity,
            name = city?.name,
            description = city?.description,
            icon = city?.icon,
            temp = city?.temp,
            temp_max = city?.temp_max,
            temp_min = city?.temp_min,
            humidity = city?.humidity,
            dateLastUpdate = Date()
        )
        appDatabase.historicWeatherCityDao().insert(historicCityEntity)
    }

    fun getHistoricCity(idCity: Int): HistoricWeatherCityEntity? {
        return appDatabase.historicWeatherCityDao().getByIdCity(idCity)
    }
}