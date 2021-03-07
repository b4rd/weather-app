package hu.simplexion.weather.ui.settings

import SettingsDataStore
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.preference.PreferenceFragmentCompat
import dagger.hilt.android.AndroidEntryPoint
import hu.simplexion.weather.R
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var userPreferencesDataStore: DataStore<Preferences>

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        preferenceManager.preferenceDataStore = SettingsDataStore(userPreferencesDataStore)
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}