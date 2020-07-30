package vboo.com.weatherlib.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The [Room] database for this app.
 */
@Database(entities = [
    CityEntity::class,
    HistoricWeatherCityEntity::class
],
    version = 1,
    exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
    abstract fun historicWeatherCityDao(): HistoricWeatherCityDao

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null
        const val DATABASE_NAME = "givemeweatherDB"

        fun buildDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                    .addCallback(DatabaseCallback(scope))
                    .build()

                INSTANCE = instance
                return instance
            }

        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database)
                    }
                }
            }
        }

        suspend fun populateDatabase(db: AppDatabase) {
            val city1 = CityEntity(2968815, "Paris", "FR", 48.853401, 2.3486, false)
            val city2 = CityEntity(2643743, "Londres", "GB", 51.50853, -0.12574, false)
            val city3 = CityEntity(6359304, "Madrid", "ES", 40.489349, -3.68275, false)
            val city4 = CityEntity(524901, "Moscou", "RU", 55.75222, 37.615555, false)
            val city5 = CityEntity(2510911, "Seville", "ES", 37.382408, -5.97613, false)
            val city6 = CityEntity(2735943, "Porto", "PT", 41.149609, -8.61099, false)
            val city7 = CityEntity(3190261, "Split", "HR", 43.508911, 16.43915, false)
            val city8 = CityEntity(5128581, "New York", "US", 40.714272, -74.005966, false)
            val city9 = CityEntity(2800867, "Bruxelles", "BE", 50.853321, 4.35144, false)
            val city10 = CityEntity(2158177, "Melbourne", "AU", -37.813999, 144.963318, false)
            val city11 = CityEntity(1850147, "Tokyo", "JP", 35.689499, 139.691711, false)
            val city12 = CityEntity(2253350, "Dakar", "SN", 14.75, -17.33333, false)
            val city13 = CityEntity(3936452, "Lima", "PE", -12.00389, -76.638893, false)

            db.cityDao().insertAll(listOf(city1, city2, city3, city4, city5, city6, city7, city8, city9, city10, city11, city12, city13))
        }


    }
}
