package hu.simplexion.weather.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.simplexion.weather.databinding.FragmentSplashBinding

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentSplashBinding.inflate(layoutInflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userPreferences.observe(viewLifecycleOwner, { userPreferences ->
            if (userPreferences.apiKey.isBlank()) {
                requireView().findNavController()
                    .navigate(SplashFragmentDirections.actionSplashFragmentToApiKeyFragment())
            } else {
                requireView().findNavController()
                    .navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
            }
        })
    }
}