package vboo.com.weatherlib.domain.repository

import vboo.com.weatherlib.domain.model.City

interface CityRepository {
    fun getListCity(): List<City>
}