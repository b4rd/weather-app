package hu.simplexion.weather.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.simplexion.weather.databinding.FragmentApiKeyBinding

@AndroidEntryPoint
class ApiKeyFragment : Fragment() {

    private lateinit var binding: FragmentApiKeyBinding
    private val viewModel by viewModels<ApiKeyViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentApiKeyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            val apiKey = binding.textviewFirst.text.toString()
            viewModel.updateApiKey(apiKey)
        }

        viewModel.apiKeySaved.observe(viewLifecycleOwner, {
            findNavController().navigate(ApiKeyFragmentDirections.actionApiKeyFragmentToMainFragment())
        })
    }
}