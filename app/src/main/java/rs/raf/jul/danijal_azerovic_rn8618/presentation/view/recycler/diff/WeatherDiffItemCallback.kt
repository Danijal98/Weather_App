package rs.raf.jul.danijal_azerovic_rn8618.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Weather

class WeatherDiffItemCallback : DiffUtil.ItemCallback<Weather>() {

    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.id == newItem.id
    }

    //TODO promeniti za maxtemp
    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.cityName == newItem.cityName
                && oldItem.maxtemp_c == newItem.maxtemp_c
                && oldItem.icon == newItem.icon
                && oldItem.date == newItem.date
    }

}