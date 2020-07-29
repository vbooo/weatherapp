package vboo.com.weatherlib.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import vboo.com.weatherlib.domain.IoDispatcher
import vboo.com.weatherlib.domain.Result
import vboo.com.weatherlib.domain.repository.CityRepository
import java.util.*
import javax.inject.Inject

/**
 * Use Case responsible to get the current weather of a city
 */
class GetCurrentCityWeatherUseCase @Inject constructor (
    private val repository: CityRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): FlowUseCase<Int, CurrentCityWeatherResult>(dispatcher) {

    override fun execute(parameters: Int): Flow<Result<CurrentCityWeatherResult>> {
        return repository.getCurrentCityWeather(parameters)
    }

}

data class CurrentCityWeatherResult (val idCity: Int?,
                                     val name: String?,
                                     val description: String?,
                                     val icon: String?,
                                     val temp: Double?,
                                     val temp_min: Double?,
                                     val temp_max: Double?,
                                     val humidity: Int?,
                                     val date: Date
)