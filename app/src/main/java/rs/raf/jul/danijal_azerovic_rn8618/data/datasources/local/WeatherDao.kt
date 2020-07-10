package rs.raf.jul.danijal_azerovic_rn8618.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.jul.danijal_azerovic_rn8618.data.models.WeatherEntity

@Dao
abstract class WeatherDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<WeatherEntity>): Completable

    @Query( "SELECT  * FROM weather WHERE cityName = :city LIMIT :days" )
    abstract fun getWeather(city: String, days: String): Observable<List<WeatherEntity>>

    @Query("DELETE FROM weather WHERE cityName = :city")
    abstract fun deleteAll(city: String)

    @Transaction
    open fun deleteAndInsertAll(entities: List<WeatherEntity>){
        deleteAll(entities[0].cityName)
        insertAll(entities).blockingAwait()
    }

    //TODO
    //abstract fun deleteOlderThanToday(): Completable

}