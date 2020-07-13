package rs.raf.jul.danijal_azerovic_rn8618.presentation.view.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.content.getSystemService
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.moshi.Moshi
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.jul.danijal_azerovic_rn8618.R
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Weather
import rs.raf.jul.danijal_azerovic_rn8618.presentation.contract.WeatherContract
import rs.raf.jul.danijal_azerovic_rn8618.presentation.view.recycler.adapter.WeatherAdapter
import rs.raf.jul.danijal_azerovic_rn8618.presentation.view.recycler.diff.WeatherDiffItemCallback
import rs.raf.jul.danijal_azerovic_rn8618.presentation.view.states.WeatherState
import rs.raf.jul.danijal_azerovic_rn8618.presentation.viewmodel.WeatherViewModel
import rs.raf.jul.danijal_azerovic_rn8618.utilities.WeatherFilter
import timber.log.Timber
import java.util.ArrayList


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val weatherViewModel: WeatherContract.ViewModel by viewModel<WeatherViewModel>()
    private lateinit var weatherAdapter: WeatherAdapter
    private lateinit var list: List<Weather>

    companion object {
        const val MESSAGE_KEY_WEATHER = "weather"
        const val MESSAGE_KEY_LIST = "list"
        var CONNECTED: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.e("onCreate")

        // This is not working atm because all time_epoch's are hardcoded in the api and older then today
        //weatherViewModel.deleteOlderThanToday()

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
            checkNetworkConnection()
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

                progress_circular.visibility = View.GONE

                if(state.weather.isEmpty()){
                    recyclerView.visibility = View.GONE
                    emptyListText.visibility = View.VISIBLE
                    return
                }

                recyclerView.visibility = View.VISIBLE
                emptyListText.visibility = View.GONE

                list = state.weather
                weatherAdapter.submitList(list)
            }
            is WeatherState.Error -> {
                //Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is WeatherState.DataFetched -> {
                //Toast.makeText(this, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is WeatherState.Loading -> {
                //Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                recyclerView.visibility = View.GONE
                emptyListText.visibility = View.GONE
                progress_circular.visibility = View.VISIBLE
            }
        }
    }

    private fun checkNetworkConnection(){
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        CONNECTED = isConnected
    }

}
