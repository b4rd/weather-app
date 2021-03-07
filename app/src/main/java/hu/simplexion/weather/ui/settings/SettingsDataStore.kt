import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.preference.PreferenceDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SettingsDataStore(private val dataStore: DataStore<Preferences>) : PreferenceDataStore() {


    override fun putString(key: String, value: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { it[stringPreferencesKey(key)] = value!! }
        }
    }

    override fun getString(key: String, defValue: String?): String? {
        return runBlocking {
            dataStore.data.map { it[stringPreferencesKey(key)] ?: defValue }.first()
        }
    }

    override fun putInt(key: String, value: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            dataStore.edit { it[intPreferencesKey(key)] = value }
        }
    }

    override fun getInt(key: String, defValue: Int): Int {
        return runBlocking {
            dataStore.data.map { it[intPreferencesKey(key)] ?: defValue }.first()
        }
    }
}