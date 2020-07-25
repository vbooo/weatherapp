package vboo.com.weatherlib.data.datasource

import vboo.com.weatherlib.data.db.AppDatabase
import vboo.com.weatherlib.data.db.CityEntityDataMapper
import vboo.com.weatherlib.domain.model.City
import javax.inject.Inject

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
}