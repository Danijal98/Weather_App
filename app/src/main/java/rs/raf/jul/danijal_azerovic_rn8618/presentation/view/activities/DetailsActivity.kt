package rs.raf.jul.danijal_azerovic_rn8618.presentation.view.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_details.*
import rs.raf.jul.danijal_azerovic_rn8618.R
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Weather

class DetailsActivity : AppCompatActivity(R.layout.activity_details), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var weather: Weather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        weather = intent.getParcelableExtra(MainActivity.MESSAGE_KEY_WEATHER)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        initBottomView()
    }

    @SuppressLint("SetTextI18n")
    private fun initBottomView() {
        cityAndDate.text = weather.cityName + ", " + weather.date
        maxTemp.text = maxTemp.text.toString() + ": " + weather.maxtemp_c.toDouble().round(1).toString() + "C"
        minTemp.text = minTemp.text.toString() + ": " + weather.mintemp_c.toDouble().round(1).toString() + "C"
        windSpeed.text = windSpeed.text.toString() + ": " + weather.maxwind_kmh.toDouble().round(1).toString() + "kmh"
        uv.text = uv.text.toString() + ": " + weather.uv.toDouble().round(1).toString()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val location = LatLng(weather.lat.toDouble(), weather.lon.toDouble())
        mMap.addMarker(MarkerOptions().position(location).title("Marker in ${weather.cityName}"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10f))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            if (item.itemId == android.R.id.home){
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

}
