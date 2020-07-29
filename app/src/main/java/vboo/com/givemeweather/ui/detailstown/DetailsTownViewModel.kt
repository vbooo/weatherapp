package vboo.com.givemeweather.ui.detailstown

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import vboo.com.weatherlib.domain.Result
import vboo.com.weatherlib.domain.data
import vboo.com.weatherlib.domain.usecases.CurrentCityWeatherResult
import vboo.com.weatherlib.domain.usecases.GetCurrentCityWeatherUseCase

/**
 * This class handles [DetailsTownFragment] UI relative data
 */
class DetailsTownViewModel @ViewModelInject constructor(
    val getCurrentCityWeatherUseCase: GetCurrentCityWeatherUseCase
):  ViewModel() {

    val currentWeather : MutableLiveData<CurrentCityWeatherResult> = MutableLiveData()
    val currentWeatherError : MutableLiveData<Boolean> = MutableLiveData(false)
    val currentWeatherLoading : MutableLiveData<Boolean> = MutableLiveData(true)

    fun getWeather(idCity: Int) {
        viewModelScope.launch {
            getCurrentCityWeatherUseCase(idCity).collect {
                currentWeather.value = it.data
                when (it)  {
                    is Result.Loading ->  currentWeatherLoading.postValue(true)
                    is Result.Error ->  {
                        currentWeatherError.postValue(true)
                        currentWeatherLoading.postValue(false)
                    }

                    is Result.Success ->  {
                        currentWeather.postValue(it.data)
                        currentWeatherLoading.postValue(false)
                        currentWeatherError.postValue(false)
                    }
                }

            }

        }
    }



}