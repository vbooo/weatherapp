package vboo.com.weatherlib.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The [Room] database for this app.
 */
@Database(entities = [
    CityEntity::class
],
    version = 1,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

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
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database)
                    }
                }
            }
        }

        suspend fun populateDatabase(db: AppDatabase) {
            val city1 = CityEntity(1, "Paris", "France", 10.0, 10.0)
            val city2 = CityEntity(2, "Londres", "Angleterre", 10.0, 10.0)
            val city3 = CityEntity(3, "Madrid", "Espagne", 10.0, 10.0)
            val city4 = CityEntity(4, "Moscou", "Russie", 10.0, 10.0)

            db.cityDao().insertAll(listOf(city1, city2, city3, city4))
        }


    }
}
