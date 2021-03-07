package hu.simplexion.weather

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

data class UserPreferences(
    val refreshInterval: Int,
    val apiKey: String
)

class UserPreferencesRepository @Inject constructor(
    private val userPreferencesDataStore: DataStore<Preferences>
) {

    private object PreferencesKeys {
        val REFRESH_INTERVAL = intPreferencesKey("refresh_interval")
        val API_KEY = stringPreferencesKey("api_key")
    }

    val userPreferencesFlow: Flow<UserPreferences> = userPreferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Logger.e("Error reading preferences.", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val refreshInterval = preferences[PreferencesKeys.REFRESH_INTERVAL] ?: 1
            val apiKey = preferences[PreferencesKeys.API_KEY] ?: ""
            UserPreferences(refreshInterval, apiKey)
        }

    suspend fun updateApiKey(apiKey: String) {
        userPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.API_KEY] = apiKey
        }
    }
}

