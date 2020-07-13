package rs.raf.jul.danijal_azerovic_rn8618.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Weather

class WeatherDiffItemCallback : DiffUtil.ItemCallback<Weather>() {

    override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
        return oldItem.cityName == newItem.cityName
                && checkTemp(oldItem, newItem)
                && oldItem.icon == newItem.icon
                && oldItem.date == newItem.date
    }

    private fun checkTemp(oldItem: Weather, newItem: Weather): Boolean {
        return if (oldItem.curr_temp != "0"){
            oldItem.curr_temp == newItem.curr_temp
        }else{
            oldItem.avgtemp_c == newItem.avgtemp_c
        }
    }

}