package rs.raf.jul.danijal_azerovic_rn8618.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.jul.danijal_azerovic_rn8618.presentation.view.states.WeatherState
import rs.raf.jul.danijal_azerovic_rn8618.utilities.WeatherFilter

interface WeatherContract {

    interface ViewModel{

        val weatherState: LiveData<WeatherState>

        fun fetchWeather(city: String, days: String)
        fun getWeather(filter: WeatherFilter)

    }

}