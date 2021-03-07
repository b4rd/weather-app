package hu.simplexion.weather.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.google.android.gms.location.LocationServices
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import hu.simplexion.weather.R
import hu.simplexion.weather.databinding.FragmentWeatherBinding
import hu.simplexion.weather.ui.settings.SettingsActivity
import kotlin.math.roundToInt

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val viewModel by viewModels<WeatherViewModel>()
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                observeWeatherInfo()
            } else {
                findNavController().popBackStack()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            observeWeatherInfo()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    private fun observeWeatherInfo() {
        viewModel.weatherInfo.observe(viewLifecycleOwner, { weatherInfo ->
            val current = weatherInfo.current
            val location = weatherInfo.location

            binding.temperature.text = current.tempC.toString()
            binding.windSpeed.text = current.windKph.toString()
            binding.windDirection.text = current.windDirection
            binding.condition.text = current.condition.conditionText
            binding.location.text = location.name
            Glide
                .with(requireContext())
                .load("https:${current.condition.conditionIconUrl}")
                .override(SIZE_ORIGINAL, SIZE_ORIGINAL)
                .into(binding.icon)

            viewModel.displayNotification(current.tempC.roundToInt())
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(requireContext(), SettingsActivity::class.java))
                true
            }
            R.id.action_refresh -> {
                viewModel.refresh()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}