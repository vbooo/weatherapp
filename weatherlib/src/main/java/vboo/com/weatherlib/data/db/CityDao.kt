package vboo.com.weatherlib.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(listCity: List<CityEntity>)

    @Query("SELECT * FROM city")
    fun getAll(): List<CityEntity>

    @Query("UPDATE city SET isFavourite = :isFavourite WHERE city.id = :idCity")
    fun updateCityAsFavourite(idCity: Int, isFavourite: Boolean)

    @Query("SELECT * FROM city WHERE isFavourite IS 1")
    fun getFavouriteCity(): List<CityEntity>

}