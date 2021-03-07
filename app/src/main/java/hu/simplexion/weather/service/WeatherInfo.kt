package hu.simplexion.weather.service

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherInfo(
    @Json(name = "location")
    val location: Location,
    @Json(name = "current")
    val current: Current,
)

@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "name")
    val name: String,
)

@JsonClass(generateAdapter = true)
data class Current(
    /** Local time when the real time data was updated. */
    @Json(name = "last_updated")
    val lastUpdated: String,
    /**	Local time when the real time data was updated in unix time.*/
    @Json(name = "last_updated_epoch")
    val lastUpdatedEpoch: Long,
    /**	Temperature in celsius*/
    @Json(name = "temp_c")
    val tempC: Double,
    /**	Temperature in fahrenheit*/
    @Json(name = "temp_f")
    val tempF: Double,
    /**	Feels like temperature in celsius*/
    @Json(name = "feelslike_c")
    val feelsLikeCelsius: Double,
    /** Feels like temperature in fahrenheit*/
    @Json(name = "feelslike_f")
    val feelsLikeFahrenheit: Double,
    @Json(name = "condition")
    val condition: Condition,
    /**	Wind speed in miles per hour*/
    @Json(name = "wind_mph")
    val windMph: Double,
    /**	Wind speed in kilometer per hour*/
    @Json(name = "wind_kph")
    val windKph: Double,
    /**	Wind direction in degrees*/
    @Json(name = "wind_degree")
    val windDegree: Int,
    /**	Wind direction as 16 point compass. e.g.: NSW*/
    @Json(name = "wind_dir")
    val windDirection: String,
    /**	Pressure in millibars*/
    @Json(name = "pressure_mb")
    val pressureMb: Double,
    /**	Pressure in inches*/
    @Json(name = "pressure_in")
    val pressureIn: Double,
    /**	Precipitation amount in millimeters*/
    @Json(name = "precip_mm")
    val precipitationMm: Double,
    /**	Precipitation amount in inches*/
    @Json(name = "precip_in")
    val precipitationIn: Double,
    /**	Humidity as percentage*/
    @Json(name = "humidity")
    val humidity: Int,
    /**	Cloud cover as percentage*/
    @Json(name = "cloud")
    val cloud: Int,
    /**	1 = Yes 0 = No*/
    @Json(name = "is_day")
    @IntAsBoolean
    val isDay: Boolean,
    /**	UV Index*/
    @Json(name = "uv")
    val uvIndex: Double,
    /**	Wind gust in miles per hour*/
    @Json(name = "gust_mph")
    val gustMph: Double,
    /**	Wind gust in kilometer per hour*/
    @Json(name = "gust_kph")
    val gustKph: Double,
)

@JsonClass(generateAdapter = true)
data class Condition(
    /**	Weather condition text*/
    @Json(name = "text")
    val conditionText: String,
    /**	Weather icon url*/
    @Json(name = "icon")
    val conditionIconUrl: String,
    /**	Weather condition unique code.*/
    @Json(name = "code")
    val conditionCode: Long,
)