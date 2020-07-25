package vboo.com.givemeweather.ui.listtowns

import android.content.Context
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_city.view.*


class CityHolder (var view: View, var context: Context) : RecyclerView.ViewHolder(view) {

    fun setNameCity(value: String) {
        view.item_city_name.text = value
    }

    fun getItem(): ConstraintLayout {
        return view.item_city
    }
}