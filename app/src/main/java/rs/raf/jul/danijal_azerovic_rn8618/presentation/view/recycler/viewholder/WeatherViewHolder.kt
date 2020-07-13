package rs.raf.jul.danijal_azerovic_rn8618.presentation.view.recycler.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.weather_item.*
import rs.raf.jul.danijal_azerovic_rn8618.R
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Weather

class WeatherViewHolder(
    override val containerView: View,
    detailsAction: (Int) -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener { detailsAction(adapterPosition) }
    }

    fun bind(weather: Weather){
        val temp = if(adapterPosition==0){
            weather.curr_temp.toDouble().round(1).toString()
        }else{
            weather.avgtemp_c.toDouble().round(1).toString()
        }
        Picasso
            .get()
            .load("https:${weather.icon}")
            .resize(150,150)
            .error(R.drawable.error_weather_icon)
            .into(weatherImg)
        weatherTemp.text = temp + "C"
        weatherCity.text = weather.cityName
        weatherDate.text = weather.date
    }

    private fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

}