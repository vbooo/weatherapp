package vboo.com.givemeweather.ui.addtown

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vboo.com.givemeweather.ui.listtowns.ClickAction
import vboo.com.weatherlib.domain.model.City
import vboo.com.weatherlib.domain.successOr
import vboo.com.weatherlib.domain.usecases.GetCityListUseCase
import vboo.com.weatherlib.domain.usecases.UpdateCityAsFavouriteUseCase

/**
 * This viewModel handles [AddTownFragment] UI related data
 */
class AddTownViewModel @ViewModelInject constructor(
    val getListCityUseCase: GetCityListUseCase,
    val updateCityAsFavouriteUseCase: UpdateCityAsFavouriteUseCase
) : ViewModel(), ClickAction {


    // list of the cities available
    var listCity: MutableLiveData<List<City>> = MutableLiveData()
    var eventCityClicked: MutableLiveData<Int> = MutableLiveData()
    var cityFavouriteUpdated: MutableLiveData<City?> = MutableLiveData()

    fun loadData() {
        viewModelScope.launch {
            getListCityUseCase(Unit).let {
                listCity.postValue(it.successOr(null))
            }
        }
    }

    override fun onCityClicked(id: Int) {

    }

    override fun onStarClicked(city: City) {
        viewModelScope.launch {
            updateCityAsFavouriteUseCase(city).let {
                cityFavouriteUpdated.postValue(it.successOr(null))
            }
        }

    }



}