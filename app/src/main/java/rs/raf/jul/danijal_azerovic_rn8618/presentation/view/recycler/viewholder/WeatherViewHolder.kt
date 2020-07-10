package rs.raf.jul.danijal_azerovic_rn8618.presentation.view.recycler.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.weather_item.*
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Weather

class WeatherViewHolder(
    override val containerView: View,
    detailsAction: (Int) -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener { detailsAction(adapterPosition) }
    }

    fun bind(weather: Weather){
        Picasso
            .get()
            .load("https:${weather.icon}")
            .resize(150,150)
            .into(weatherImg)
        weatherTemp.text = weather.maxtemp_c + "C"
        weatherCity.text = weather.cityName
        weatherDate.text = weather.date
    }

}