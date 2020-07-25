package vboo.com.weatherlib.domain.usecases

import kotlinx.coroutines.CoroutineDispatcher
import vboo.com.weatherlib.domain.IoDispatcher
import vboo.com.weatherlib.domain.model.City
import vboo.com.weatherlib.domain.repository.CityRepository
import javax.inject.Inject

class GetCityListUseCase @Inject constructor (
    private val repository: CityRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
): UseCase<Unit, List<City>>(dispatcher) {

    override suspend fun execute(parameters: Unit): List<City> {
        return repository.getListCity()
    }

}