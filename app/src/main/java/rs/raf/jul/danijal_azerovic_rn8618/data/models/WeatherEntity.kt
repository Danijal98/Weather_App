package rs.raf.jul.danijal_azerovic_rn8618.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "weather")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @Json(name = "city_name") val cityName: String,
    val lat: String,
    val lon: String,
    val date: String,
    val icon: String,
    val maxtemp_c: String,
    val mintemp_c: String,
    val maxwind_kmh: String,
    val uv: String
)