package vboo.com.givemeweather.ui.listtowns

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vboo.com.weatherlib.domain.model.City
import vboo.com.weatherlib.domain.successOr
import vboo.com.weatherlib.domain.usecases.GetCityListUseCase

class ListTownViewModel @ViewModelInject constructor(
    val getListCityUseCase: GetCityListUseCase
) : ViewModel() {

    // fake user of the application
    var listCity: MutableLiveData<List<City>> = MutableLiveData()

    init {
        /**
         * We launch a coroutine (based on a viewModelScope) to call the [GetCityListUseCase].
         * The Response is posted in the listCity LiveData. We choose to return "null" if the Response
         * returns an error
         */
        viewModelScope.launch {
            getListCityUseCase(Unit).let {
                listCity.postValue(it.successOr(null))
            }
        }
    }
}