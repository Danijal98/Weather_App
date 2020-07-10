package rs.raf.jul.danijal_azerovic_rn8618.data.models.weather

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Day(
    val maxtemp_c: String,
    val mintemp_c: String,
    val maxwind_kph: String,
    val condition: Condition,
    val uv: String
)