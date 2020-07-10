package rs.raf.jul.danijal_azerovic_rn8618.presentation.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.jul.danijal_azerovic_rn8618.R
import rs.raf.jul.danijal_azerovic_rn8618.presentation.contract.WeatherContract
import rs.raf.jul.danijal_azerovic_rn8618.presentation.view.recycler.adapter.WeatherAdapter
import rs.raf.jul.danijal_azerovic_rn8618.presentation.view.recycler.diff.WeatherDiffItemCallback
import rs.raf.jul.danijal_azerovic_rn8618.presentation.view.states.WeatherState
import rs.raf.jul.danijal_azerovic_rn8618.presentation.viewmodel.WeatherViewModel
import rs.raf.jul.danijal_azerovic_rn8618.utilities.WeatherFilter
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val weatherViewModel: WeatherContract.ViewModel by viewModel<WeatherViewModel>()
    private lateinit var weatherAdapter: WeatherAdapter

    companion object {
        const val MESSAGE_KEY_WEATHER = "weather"
    }

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
            renderState(it)
        })
    }

    private fun initListeners() {
        btn_show.setOnClickListener {
            if (cityText.text.isBlank() || daysText.text.isBlank()) {
                Toast.makeText(this, "City or days field is empty!", Toast.LENGTH_SHORT).show()
                cityText.requestFocus()
                return@setOnClickListener
            }
            val days: Int = Integer.parseInt(daysText.text.toString())
            val city = cityText.text.toString()
            if (days < 1 || days > 10) {
                Toast.makeText(this, "Days field must be between 1 and 10!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            cityText.text.clear()
            daysText.text.clear()
            weatherViewModel.getWeather(WeatherFilter(city.trim(), days.toString()))
            weatherViewModel.fetchWeather(city.trim(), days.toString())
        }
    }

    private fun initRecycler() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        weatherAdapter = WeatherAdapter(WeatherDiffItemCallback()){
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(MESSAGE_KEY_WEATHER, it)
            startActivity(intent)
        }
        recyclerView.adapter = weatherAdapter
    }

    private fun renderState(state: WeatherState){
        when (state) {
            is WeatherState.Success -> {
                if(state.weather.isEmpty()){
                    Timber.e("Prazan niz")
                    recyclerView.visibility = View.GONE
                    emptyListText.visibility = View.VISIBLE
                    return
                }
                recyclerView.visibility = View.VISIBLE
                emptyListText.visibility = View.GONE
                Timber.e("${state.weather}")
                weatherAdapter.submitList(state.weather)
            }
            is WeatherState.Error -> {
                //Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is WeatherState.DataFetched -> {
                //Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is WeatherState.Loading -> {
                //Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.e("onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Timber.e("onRestoreInstanceState")
        //TODO
    }

}
