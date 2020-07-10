package rs.raf.jul.danijal_azerovic_rn8618.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.jul.danijal_azerovic_rn8618.data.models.Resource
import rs.raf.jul.danijal_azerovic_rn8618.data.repositories.WeatherRepository
import rs.raf.jul.danijal_azerovic_rn8618.presentation.contract.WeatherContract
import rs.raf.jul.danijal_azerovic_rn8618.presentation.view.states.WeatherState
import rs.raf.jul.danijal_azerovic_rn8618.utilities.WeatherFilter
import timber.log.Timber

class WeatherViewModel (
    private val weatherRepository: WeatherRepository
): ViewModel(), WeatherContract.ViewModel {

    private val subscriptions = CompositeDisposable()
    override val weatherState: MutableLiveData<WeatherState> = MutableLiveData()

    private val publishSubject: PublishSubject<WeatherFilter> = PublishSubject.create()

    init {
        val subscrition = publishSubject
            .switchMap {
                weatherRepository
                    .getByNameAndDays(it.city, it.days)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    weatherState.value = WeatherState.Success(it)
                },
                {
                    weatherState.value = WeatherState.Error("Error happened while getting data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscrition)
    }

    override fun fetchWeather(city: String, days: String) {
        val subscription = weatherRepository
            .fetchByNameAndDays(city, days)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> weatherState.value = WeatherState.Loading
                        is Resource.Success -> weatherState.value = WeatherState.DataFetched
                        is Resource.Error -> weatherState.value = WeatherState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    weatherState.value = WeatherState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
            subscriptions.add(subscription)
    }

    override fun getWeather(filter: WeatherFilter) {
        publishSubject.onNext(filter)
    }

    /*
    override fun getWeather(city: String, days: String) {
        val subscription = weatherRepository
            .getByNameAndDays(city, days)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    weatherState.value = WeatherState.Success(it)
                    Timber.e("$it")
                },
                {
                    weatherState.value = WeatherState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }
     */

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }


}