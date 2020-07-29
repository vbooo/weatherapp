package vboo.com.givemeweather.ui.listtowns

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vboo.com.weatherlib.domain.model.City
import vboo.com.weatherlib.domain.successOr
import vboo.com.weatherlib.domain.usecases.GetCityListUseCase

/**
 * This class handles [ListTownFragment] UI relative data
 */
class ListTownViewModel @ViewModelInject constructor(
    val getListCityUseCase: GetCityListUseCase
) : ViewModel(), ClickAction {

    // list of the cities available
    var listCity: MutableLiveData<List<City>> = MutableLiveData()
    var eventCityClicked: MutableLiveData<Int> = MutableLiveData()

    fun loadData() {
        viewModelScope.launch {
            getListCityUseCase(Unit).let {
                listCity.postValue(it.successOr(null))
            }
        }
    }

    override fun onCityClicked(id: Int) {
        eventCityClicked.value = id
    }
}