package vboo.com.weatherlib.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import vboo.com.weatherlib.domain.IoDispatcher
import vboo.com.weatherlib.domain.model.City
import vboo.com.weatherlib.domain.repository.CityRepository
import javax.inject.Inject

/**
 * UseCase responsible to update a [City] as favourite or not
 */
class UpdateCityAsFavouriteUseCase @Inject constructor (
    private val repository: CityRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): UseCase<City, City>(dispatcher) {

    override suspend fun execute(parameters: City): City {
        return repository.updateCityAsFavourite(parameters)
    }

}