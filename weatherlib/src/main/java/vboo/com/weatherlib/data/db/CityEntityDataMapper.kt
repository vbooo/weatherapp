package vboo.com.weatherlib.data.db

import vboo.com.weatherlib.domain.model.City
import javax.inject.Inject

class CityEntityDataMapper @Inject constructor() {

    fun transform(cityEntity: CityEntity): City {
        return cityEntity.let {
            City(it.id, it.name, it.country, it.lat, it.lon)
        }
    }
}