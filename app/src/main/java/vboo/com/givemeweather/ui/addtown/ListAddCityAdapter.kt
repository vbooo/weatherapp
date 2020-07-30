package vboo.com.givemeweather.ui.addtown

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.qualifiers.ApplicationContext
import vboo.com.givemeweather.R
import vboo.com.givemeweather.ui.listtowns.CityAddHolder
import vboo.com.givemeweather.ui.listtowns.ClickAction
import vboo.com.weatherlib.domain.model.City

/**
 * This class handles city list information
 */
class ListAddCityAdapter (@ApplicationContext val context: Context,
                          private val listener: ClickAction
): RecyclerView.Adapter<CityAddHolder>() {

    // This list contains all the Albums
    var listCity: List<City>? = null
        set(value) {
            value?.let {
                field = value
                notifyDataSetChanged()
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAddHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_city, parent, false)
        return CityAddHolder(view, context)
    }

    override fun getItemCount(): Int {
        return listCity?.size ?: 0
    }


    override fun onBindViewHolder(holder: CityAddHolder, position: Int) {

        listCity?.let { list ->
            // display the city name
            list[position].name.let {  holder.setNameCity(it) }

            if (list[position].isFavourite) {
                holder.getStarIcon().setImageResource(R.drawable.star_yellow)
            } else {
                holder.getStarIcon().setImageResource(R.drawable.star_empty)
            }

            holder.getStarIcon().setOnClickListener {
                listener.onStarClicked(list[position])
                if (list[position].isFavourite) {
                    holder.getStarIcon().setImageResource(R.drawable.star_empty)
                } else {
                    holder.getStarIcon().setImageResource(R.drawable.star_yellow)
                }
            }
        }

    }
}