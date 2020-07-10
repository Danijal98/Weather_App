package rs.raf.jul.danijal_azerovic_rn8618.data.repositories

import io.reactivex.Observable
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Resource
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Weather

interface WeatherRepository {

    fun fetchByNameAndDays(city: String, days: String): Observable<Resource<Unit>>
    fun getByNameAndDays(city: String, days: String): Observable<List<Weather>>

}