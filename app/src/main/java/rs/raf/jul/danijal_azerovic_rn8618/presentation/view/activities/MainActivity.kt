package rs.raf.jul.danijal_azerovic_rn8618.presentation.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.jul.danijal_azerovic_rn8618.R
import rs.raf.jul.danijal_azerovic_rn8618.presentation.contract.WeatherContract
import rs.raf.jul.danijal_azerovic_rn8618.presentation.viewmodel.WeatherViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val weatherViewModel: WeatherContract.ViewModel by viewModel<WeatherViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initRecycler()
        initListeners()
        initObservers()
    }

    private fun initObservers() {
        weatherViewModel.weatherState.observe(this, Observer {
            Timber.e(it.toString())
        })
        weatherViewModel.getWeather("Belgrade", "3")
        weatherViewModel.fetchWeather("Belgrade","3")
    }

    private fun initListeners() {

    }

    private fun initRecycler() {

    }


}
