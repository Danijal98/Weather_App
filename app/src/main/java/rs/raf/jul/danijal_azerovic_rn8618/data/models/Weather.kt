package rs.raf.jul.danijal_azerovic_rn8618.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather(
    val id: String,
    val cityName: String,
    val lat: String,
    val lon: String,
    val date: String,
    val icon: String,
    val curr_temp: String = "",
    val maxtemp_c: String,
    val mintemp_c: String,
    val avgtemp_c: String,
    val maxwind_kmh: String,
    val uv: String
) : Parcelable