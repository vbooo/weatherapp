package vboo.com.weatherlib.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoricWeatherCityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(listCity: HistoricWeatherCityEntity)

    @Query("SELECT * FROM historicWeatherCityEntity WHERE idCity IS :id")
    fun getByIdCity(id: Int): HistoricWeatherCityEntity

    @Query("DELETE FROM historicWeatherCityEntity WHERE idCity IS :id")
    fun deleteByIdCity(id: Int)

}