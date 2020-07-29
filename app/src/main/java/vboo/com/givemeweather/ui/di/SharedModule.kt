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
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import vboo.com.givemeweather.utils.NetworkUtilsImpl
import vboo.com.weatherlib.data.NetworkUtils
import vboo.com.weatherlib.data.datasource.CityLocalDataSource
import vboo.com.weatherlib.data.datasource.CityRemoteDataSource
import vboo.com.weatherlib.data.db.AppDatabase
import vboo.com.weatherlib.data.network.AppService
import vboo.com.weatherlib.data.network.AppService.Companion.baseUrl
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
    fun provideNetworkUtils(
        @ApplicationContext context: Context
    ): NetworkUtils {
        return NetworkUtilsImpl(context)
    }

    @Singleton
    @Provides
    fun provideCityRepository(
        cityLocalDataSource: CityLocalDataSource,
        cityDataSource: CityRemoteDataSource,
        networkUtils: NetworkUtils
    ): CityRepository {
        return CityRepositoryImpl(
            cityLocalDataSource,
            cityDataSource,
            networkUtils
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

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesAlbumApi(retrofit: Retrofit): AppService = retrofit.create(
        AppService::class.java)


}