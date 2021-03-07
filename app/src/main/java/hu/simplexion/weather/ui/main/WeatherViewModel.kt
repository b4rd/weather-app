package hu.simplexion.weather.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.simplexion.weather.UserPreferencesRepository
import hu.simplexion.weather.service.WeatherInfo
import hu.simplexion.weather.service.WeatherService
import hu.simplexion.weather.service.Notifications
import hu.simplexion.weather.utils.interval
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@HiltViewModel
class WeatherViewModel
@Inject constructor(
    @ApplicationContext private val context: Context,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val weatherService: WeatherService,
    private val notifications: Notifications,
) : ViewModel() {

    private val manualRefreshFlow = MutableSharedFlow<String>()

    @ExperimentalCoroutinesApi
    val weatherInfo: LiveData<WeatherInfo> = liveData {
        val userPreferences = userPreferencesRepository.userPreferencesFlow.first()
        val apiKey = userPreferences.apiKey
        val refreshInterval = userPreferences.refreshInterval

        val intervalFlow = interval(refreshInterval * 60 * 1000L, 0L)
        emitSource(merge(intervalFlow, manualRefreshFlow.onStart { emit(SIGNAL) })
            .map {
                getWeatherInfo(apiKey)
            }.catch { e ->
                Logger.e("Error white retrieving weather info", e)
            }.asLiveData()
        )
    }

    fun displayNotification(temperature: Int) {
        notifications.displayNotification(temperature)
    }

    fun refresh() {
        viewModelScope.launch {
            manualRefreshFlow.emit(SIGNAL)
        }
    }

    private suspend fun getWeatherInfo(apiKey: String): WeatherInfo {
        val lastLocation =
            LocationServices.getFusedLocationProviderClient(context).awaitLastLocation()
        val q = lastLocation.let { "${it.latitude},${it.longitude}" }
        val lang = Locale.getDefault().language
        return weatherService.getWeatherInfo(q, lang, apiKey)
    }

    @SuppressLint("MissingPermission")
    suspend fun FusedLocationProviderClient.awaitLastLocation(): Location =
        suspendCancellableCoroutine { continuation ->
            lastLocation.addOnSuccessListener { location ->
                continuation.resume(location)
            }.addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }
        }

    companion object {
        const val SIGNAL = ""
    }
}