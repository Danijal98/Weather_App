package rs.raf.jul.danijal_azerovic_rn8618.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.jul.danijal_azerovic_rn8618.data.models.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDB : RoomDatabase(){
    abstract fun getWeatherDao(): WeatherDao
}