package rs.raf.jul.danijal_azerovic_rn8618.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.jul.danijal_azerovic_rn8618.R
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Weather
import rs.raf.jul.danijal_azerovic_rn8618.presentation.view.recycler.diff.WeatherDiffItemCallback
import rs.raf.jul.danijal_azerovic_rn8618.presentation.view.recycler.viewholder.WeatherViewHolder
import kotlin.math.max

class WeatherAdapter (
    weatherDiffItemCallback: WeatherDiffItemCallback,
    private val detailsAction: (Weather) -> Unit): ListAdapter<Weather, WeatherViewHolder>(weatherDiffItemCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val containerView = layoutInflater.inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(containerView) {
            val weather = getItem(max(it,0))
            detailsAction(weather)
        }
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}