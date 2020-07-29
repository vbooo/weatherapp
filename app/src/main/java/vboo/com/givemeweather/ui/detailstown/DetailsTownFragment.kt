package vboo.com.givemeweather.ui.detailstown

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details_town.*
import vboo.com.givemeweather.R
import vboo.com.weatherlib.data.NetworkUtils
import vboo.com.weatherlib.domain.usecases.CurrentCityWeatherResult
import java.text.SimpleDateFormat
import javax.inject.Inject
import kotlin.math.roundToInt

/**
 * This fragment displays weather data related to one city
 */
@AndroidEntryPoint
class DetailsTownFragment : Fragment() {

    private val viewModel: DetailsTownViewModel by viewModels()

    private val args: DetailsTownFragmentArgs by navArgs()

    @Inject  lateinit var networkUtils: NetworkUtils

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_town, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = DetailsTownFragmentDirections.actionSecondFragmentToFirstFragment()
            findNavController().navigate(action)
        }

        viewModel.getWeather(args.idCity)

        viewModel.currentWeatherLoading.observe(viewLifecycleOwner,
            Observer {
                if (it == true) {
                    fragment_details_town_progress_bar.visibility = View.VISIBLE
                } else {
                    fragment_details_town_progress_bar.visibility = View.GONE
                }
            })

        viewModel.currentWeather.observe(viewLifecycleOwner,
            Observer {
                it?.let {
                    updateUI(it)
                }
            })

        viewModel.currentWeatherError.observe(viewLifecycleOwner,
            Observer {

                if (it == true) {
                    displayError()
                }

            })

    }

    private fun displayError() {
        fragment_details_town_error.visibility = View.VISIBLE
        fragment_details_town_layout.visibility = View.GONE
        fragment_details_town_error.text = context?.resources?.getString(R.string.error_occured)
    }

    private fun updateUI(it: CurrentCityWeatherResult) {
        fragment_details_town_name.text = it.name
        setIcon(it.icon)
        fragment_details_town_description.text = it.description

        val tempCelcius = kalvinToCelcius(it.temp)?.roundToInt().toString()
        fragment_details_town_temp.text =
            context?.resources?.getString(R.string.temp_celcius, tempCelcius)


        fragment_details_town_temp_max.text =
            context?.resources?.getString(R.string.temp_max, kalvinToCelcius(it.temp_max)?.roundToInt())
        fragment_details_town_temp_min.text =
            context?.resources?.getString(R.string.temp_min, kalvinToCelcius(it.temp_min)?.roundToInt())
        fragment_details_town_humidity.text =
            context?.resources?.getString(R.string.humidity, it.humidity)

        if (!networkUtils.hasNetworkConnection()) {

            val pattern = "dd-MM-yyyy HH:mm"
            val simpleDateFormat = SimpleDateFormat(pattern)
            val date = simpleDateFormat.format(it.date)

            fragment_details_town_network_info.visibility = View.VISIBLE
            fragment_details_town_network_info.text = context?.resources?.getString(R.string.network_info, date)
        }
    }

    private fun kalvinToCelcius(temp: Double?): Double? {
        return temp?.let {
            temp - 273.15
        }

    }

    private fun setIcon(icon: String?) {
        val resource = when (icon) {
            "01d" -> R.drawable.weather01d
            "01n" -> R.drawable.weather01n
            "02d" -> R.drawable.weather02d
            "02n" -> R.drawable.weather02n
            "03d" -> R.drawable.weather03d
            "03n" -> R.drawable.weather03n
            "04d" -> R.drawable.weather04d
            "04n" -> R.drawable.weather04n
            "09d" -> R.drawable.weather09d
            "09n" -> R.drawable.weather09n
            "10d" -> R.drawable.weather10d
            "10n" -> R.drawable.weather10n
            "11d" -> R.drawable.weather11d
            "11n" -> R.drawable.weather11n
            "13d" -> R.drawable.weather13d
            "13n" -> R.drawable.weather13n
            "50d" -> R.drawable.weather50d
            "50n" -> R.drawable.weather50n
            else -> R.drawable.no_weather
        }

        fragment_details_town_icon.setImageResource(resource)
    }
}