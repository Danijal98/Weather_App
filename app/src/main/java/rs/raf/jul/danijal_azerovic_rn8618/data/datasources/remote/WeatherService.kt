package rs.raf.jul.danijal_azerovic_rn8618.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.jul.danijal_azerovic_rn8618.application.WeatherApp.Companion.API_KEY
import rs.raf.jul.danijal_azerovic_rn8618.data.models.WeatherResponse

interface WeatherService {

    @GET("forecast.json?")
    fun getAll(@Query("q")city: String, @Query("days")days: String): Observable<WeatherResponse>

}