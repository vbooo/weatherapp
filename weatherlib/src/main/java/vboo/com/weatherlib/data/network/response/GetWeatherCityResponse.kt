package vboo.com.weatherlib.data.network.response

data class GetWeatherCityResponse (val name: String?, val id: Int?, val weather: List<WeatherResponse>?, val main: MainResponse?)