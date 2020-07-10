package rs.raf.jul.danijal_azerovic_rn8618.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.jul.danijal_azerovic_rn8618.data.datasources.local.WeatherDB
import rs.raf.jul.danijal_azerovic_rn8618.data.datasources.remote.WeatherService
import rs.raf.jul.danijal_azerovic_rn8618.data.repositories.WeatherRepository
import rs.raf.jul.danijal_azerovic_rn8618.data.repositories.WeatherRepositoryImpl
import rs.raf.jul.danijal_azerovic_rn8618.presentation.viewmodel.WeatherViewModel

val weatherModule = module {

    viewModel { WeatherViewModel(weatherRepository = get()) }

    single<WeatherRepository> { WeatherRepositoryImpl(localDataSource = get(), remoteDataSource = get()) }

    single {get<WeatherDB>().getWeatherDao()}

    single<WeatherService> {create(retrofit = get())}

}