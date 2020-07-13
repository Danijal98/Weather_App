package rs.raf.jul.danijal_azerovic_rn8618.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.jul.danijal_azerovic_rn8618.data.datasources.local.WeatherDao
import rs.raf.jul.danijal_azerovic_rn8618.data.datasources.remote.WeatherService
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Resource
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Weather
import rs.raf.jul.danijal_azerovic_rn8618.data.models.WeatherEntity
import rs.raf.jul.danijal_azerovic_rn8618.data.models.WeatherResponse
import rs.raf.jul.danijal_azerovic_rn8618.utilities.WeatherFilter
import timber.log.Timber

class WeatherRepositoryImpl (
    private val localDataSource: WeatherDao,
    private val remoteDataSource: WeatherService
): WeatherRepository {

    override fun fetchByNameAndDays(city: String, days: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAll(city,days)
            .doOnNext {
                Timber.e("Fetching data...")
                val entities: List<WeatherEntity> = convertResponseToEntity(it)
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getByNameAndDays(city: String, days: String): Observable<List<Weather>> {
        return localDataSource
            .getWeather(city,days)
            .map {
                it.map {
                    Weather(
                        id = it.id.toString(),
                        cityName = it.cityName,
                        lat = it.lat,
                        lon = it.lon,
                        date = it.date,
                        icon = it.icon,
                        curr_temp = it.curr_temp,
                        maxtemp_c = it.maxtemp_c,
                        mintemp_c = it.mintemp_c,
                        avgtemp_c = it.avgtemp_c,
                        maxwind_kmh = it.maxwind_kmh,
                        uv = it.uv
                    )
                }
            }
    }

    override fun deleteOlderThanToday(): Completable {
        return Completable.fromCallable {
            localDataSource.deleteOlderThanToday()
        }
    }

    private fun convertResponseToEntity(response: WeatherResponse): List<WeatherEntity>{
        val entities: MutableList<WeatherEntity> = mutableListOf()
        val location = response.location
        val forecast = response.forecast
        val current = response.current
        val forecastDay = forecast.forecastDay
        for ( (index, fd) in forecastDay.withIndex()){
            val day = fd.day
            val condition = day.condition
            val icon = if (index == 0) current.condition.icon else condition.icon
            val currTemp = if (index == 0) current.temp_c else "0"
            entities.add(WeatherEntity(
                0,
                location.name,
                location.lat,
                location.lon,
                fd.date,
                fd.date_epoch,
                icon,
                currTemp,
                day.maxtemp_c,
                day.mintemp_c,
                day.avgtemp_c,
                day.maxwind_kph,
                day.uv))
        }
        return entities
    }

}