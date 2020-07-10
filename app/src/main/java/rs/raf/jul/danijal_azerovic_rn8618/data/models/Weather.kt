package rs.raf.jul.danijal_azerovic_rn8618.data.models

data class Weather(
    val cityName: String,
    val lat: String,
    val lon: String,
    val date: String,
    val icon: String,
    val maxtemp_c: String,
    val mintemp_c: String,
    val maxwind_kmh: String,
    val uv: String
)

//TODO add current temp