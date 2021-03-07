package hu.simplexion.weather.service

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class IntAsBoolean

class IntAsBooleanAdapter {
    @ToJson
    fun toJson(@IntAsBoolean b: Boolean): Int {
        return if (b) 1 else 0
    }

    @FromJson
    @IntAsBoolean
    fun fromJson(i: Int): Boolean {
        return i != 0
    }
}