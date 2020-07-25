package vboo.com.givemeweather.ui.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import vboo.com.weatherlib.data.datasource.CityLocalDataSource
import vboo.com.weatherlib.data.db.AppDatabase
import vboo.com.weatherlib.data.repository.CityRepositoryImpl
import vboo.com.weatherlib.domain.ApplicationScope
import vboo.com.weatherlib.domain.IoDispatcher
import vboo.com.weatherlib.domain.repository.CityRepository
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class SharedModule {

    @Singleton
    @Provides
    fun provideCityRepository(
        userLocalDataSource: CityLocalDataSource
    ): CityRepository {
        return CityRepositoryImpl(
            userLocalDataSource
        )
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context, @ApplicationScope applicationScope: CoroutineScope): AppDatabase {
        return AppDatabase.buildDatabase(context, applicationScope)
    }

    @ApplicationScope
    @Singleton
    @Provides
    fun providesApplicationScope(
        @IoDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

}