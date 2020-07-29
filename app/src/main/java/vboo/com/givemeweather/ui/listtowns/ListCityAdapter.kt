package vboo.com.givemeweather.ui.listtowns

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import vboo.com.givemeweather.R
import vboo.com.weatherlib.domain.model.City

/**
 * This class handles city list information
 */
class ListCityAdapter (@ApplicationContext val context: Context,
                    private val listener: ClickAction): RecyclerView.Adapter<CityHolder>() {

    // This list contains all the Albums
    var listCity: List<City>? = null
        set(value) {
            value?.let {
                field = value
                notifyDataSetChanged()
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)
        return CityHolder(view, context)
    }

    override fun getItemCount(): Int {
        return listCity?.size ?: 0
    }


    override fun onBindViewHolder(holder: CityHolder, position: Int) {

        listCity?.let { list ->
            // display the city name
            list[position].name.let {  holder.setNameCity(it) }

            holder.getItem().setOnClickListener {
                listener.onCityClicked(list[position].id)
            }
        }

    }
}