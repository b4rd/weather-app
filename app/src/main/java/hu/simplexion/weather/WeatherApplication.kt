package hu.simplexion.weather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

@HiltAndroidApp
class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
    }
}