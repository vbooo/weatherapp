package vboo.com.weatherlib.data.repository

import vboo.com.weatherlib.data.datasource.CityLocalDataSource
import vboo.com.weatherlib.domain.model.City
import vboo.com.weatherlib.domain.repository.CityRepository
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val datasourceCityLocal: CityLocalDataSource
): CityRepository
{
    override fun getListCity(): List<City> {
        return datasourceCityLocal.getListCity()
    }
}