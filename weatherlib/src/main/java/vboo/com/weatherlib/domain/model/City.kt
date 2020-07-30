package vboo.com.weatherlib.domain.model

/**
 * This class handles main city informations
 */
data class City(var id: Int,
                var name: String,
                var country: String,
                var lat: Double,
                var lon: Double,
                var isFavourite: Boolean
)