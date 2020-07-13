package rs.raf.jul.danijal_azerovic_rn8618.data.models

import com.squareup.moshi.JsonClass
import rs.raf.jul.danijal_azerovic_rn8618.data.models.weather.Current
import rs.raf.jul.danijal_azerovic_rn8618.data.models.weather.Forecast
import rs.raf.jul.danijal_azerovic_rn8618.data.models.weather.Location

@JsonClass(generateAdapter = true)
data class WeatherResponse(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)