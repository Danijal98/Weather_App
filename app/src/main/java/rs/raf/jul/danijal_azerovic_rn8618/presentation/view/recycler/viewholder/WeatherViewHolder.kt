package rs.raf.jul.danijal_azerovic_rn8618.presentation.view.recycler.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.weather_item.*
import rs.raf.jul.danijal_azerovic_rn8618.R
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Weather
import timber.log.Timber

class WeatherViewHolder(
    override val containerView: View,
    detailsAction: (Int) -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    init {
        containerView.setOnClickListener { detailsAction(adapterPosition) }
    }

    fun bind(weather: Weather){
        //TODO kada je adapterPosition 0 staviti current temp, inace average
        Picasso
            .get()
            .load("https:${weather.icon}")
            .placeholder(R.drawable.ic_launcher_background)
            .resize(150,150)
            .into(weatherImg)
        weatherTemp.text = weather.maxtemp_c + "C"
        weatherCity.text = weather.cityName
        weatherDate.text = weather.date
    }

}