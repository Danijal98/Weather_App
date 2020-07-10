package rs.raf.jul.danijal_azerovic_rn8618.data.models.weather

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(
    val name: String,
    val lat: String,
    val lon: String
)