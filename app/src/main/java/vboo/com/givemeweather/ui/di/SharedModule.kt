package vboo.com.givemeweather.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import vboo.com.weatherlib.data.datasource.CityLocalDataSource
import vboo.com.weatherlib.data.repository.CityRepositoryImpl
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
}