package vboo.com.givemeweather.ui.listtowns

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vboo.com.weatherlib.domain.model.City
import vboo.com.weatherlib.domain.successOr
import vboo.com.weatherlib.domain.usecases.GetFavouriteCityListUseCase

/**
 * This class handles [ListTownFragment] UI relative data
 */
class ListTownViewModel @ViewModelInject constructor(
    val getFavouriteListCityUseCase: GetFavouriteCityListUseCase
) : ViewModel(), ClickAction {

    // list of the cities available
    var listCity: MutableLiveData<List<City>> = MutableLiveData()
    var eventCityClicked: MutableLiveData<Int> = MutableLiveData()

    fun loadFavouriteCities() {
        viewModelScope.launch {
            getFavouriteListCityUseCase(Unit).let {
                listCity.postValue(it.successOr(null))
            }
        }
    }

    override fun onCityClicked(id: Int) {
        eventCityClicked.value = id
    }

    override fun onStarClicked(city: City) {

    }
}