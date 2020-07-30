package vboo.com.givemeweather.ui.listtowns

import vboo.com.weatherlib.domain.model.City

/**
 * Callback for city list element click
 */
interface ClickAction {
    fun onCityClicked(id: Int)
    fun onStarClicked(city: City)
}