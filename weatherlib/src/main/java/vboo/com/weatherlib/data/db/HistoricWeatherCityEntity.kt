package vboo.com.weatherlib.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "historicWeatherCityEntity")
data class HistoricWeatherCityEntity (
    @PrimaryKey(autoGenerate = false) var idCity: Int?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "icon") val icon: String?,
    @ColumnInfo(name = "temp") val temp: Double?,
    @ColumnInfo(name = "temp_max") val temp_max: Double?,
    @ColumnInfo(name = "temp_min") val temp_min: Double?,
    @ColumnInfo(name = "humidity") val humidity: Int?,
    @ColumnInfo(name = "dateLastUpdate") val dateLastUpdate: Date
)