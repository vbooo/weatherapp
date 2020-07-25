package vboo.com.weatherlib.domain.model

/**
 * This class handles main city informations
 */
data class City(val id: Int, val name: String, val country: String, val lat: Double, val lon: Double)