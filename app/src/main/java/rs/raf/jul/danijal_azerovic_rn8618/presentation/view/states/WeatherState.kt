package rs.raf.jul.danijal_azerovic_rn8618.presentation.view.states

import rs.raf.jul.danijal_azerovic_rn8618.data.models.Weather

sealed class WeatherState {
    object Loading: WeatherState()
    object DataFetched: WeatherState()
    data class Success(val weather: List<Weather>): WeatherState()
    data class Error(val message: String): WeatherState()
}