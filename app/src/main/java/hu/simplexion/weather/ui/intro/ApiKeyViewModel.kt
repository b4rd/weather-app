package hu.simplexion.weather.ui.intro

import androidx.lifecycle.*
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.simplexion.weather.UserPreferencesRepository
import hu.simplexion.weather.service.WeatherInfo
import hu.simplexion.weather.service.WeatherService
import hu.simplexion.weather.utils.SingleLiveEvent
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiKeyViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    val apiKeySaved = SingleLiveEvent<Void>()

    fun updateApiKey(apiKey: String) {
        viewModelScope.launch {
            userPreferencesRepository.updateApiKey(apiKey)
            apiKeySaved.call()
        }
    }
}