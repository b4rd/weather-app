package hu.simplexion.weather.ui.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.simplexion.weather.UserPreferencesRepository
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    val userPreferences = userPreferencesRepository.userPreferencesFlow.asLiveData()
}