package vboo.com.weatherlib.data.datasource

import vboo.com.weatherlib.domain.model.City
import javax.inject.Inject

class CityLocalDataSource @Inject constructor()
{
    fun getListCity(): List<City> {
        val city1 = City(1, "Paris", "France", 10, 10)
        val city2 = City(2, "Londres", "Angleterre", 10, 10)
        val city3 = City(3, "Madrid", "Espagne", 10, 10)
        val city4 = City(4, "Moscou", "Russie", 10, 10)
        return listOf(city1, city2, city3, city4)
    }
}