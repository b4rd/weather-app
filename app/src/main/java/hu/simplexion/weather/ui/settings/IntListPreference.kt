package hu.simplexion.weather.ui.settings

import android.content.Context
import android.util.AttributeSet
import androidx.preference.ListPreference

class IntListPreference(
    context: Context,
    attrs: AttributeSet
) : ListPreference(context, attrs) {

    override fun persistString(value: String): Boolean {
        val intValue = value.toInt()
        return persistInt(intValue)
    }

    override fun getPersistedString(defaultReturnValue: String?): String {
        val intValue = if (defaultReturnValue != null) {
            val intDefaultReturnValue = defaultReturnValue.toInt()
            getPersistedInt(intDefaultReturnValue)
        } else {
            if (getPersistedInt(0) == getPersistedInt(1)) {
                getPersistedInt(0)
            } else {
                throw IllegalArgumentException("Cannot get an int without a default return value")
            }
        }
        return intValue.toString()
    }
}