package vboo.com.givemeweather.ui.listtowns

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_add_city.view.*
import kotlinx.android.synthetic.main.item_city.view.item_city_name

/**
 * ViewHolder for city list
 */
class CityAddHolder (var view: View, var context: Context) : RecyclerView.ViewHolder(view) {

    fun setNameCity(value: String) {
        view.item_city_name.text = value
    }

    fun getStarIcon(): ImageView {
        return view.item_city_star
    }
}