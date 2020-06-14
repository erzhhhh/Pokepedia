package com.example.pokepedia.pokemonDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.example.pokepedia.App
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

private const val URL = "url"

class PokemonDetailsFragment : BottomSheetDialogFragment() {

    companion object {

        fun newInstance(url: String) =
            PokemonDetailsFragment().apply {
                arguments = bundleOf(URL to url)
            }
    }

    @Inject
    lateinit var dataSource: PokemonService

    private val viewModel by viewModels<PokemonDetailsViewModel> {
        PokemonDetailsViewModelFactory(
            dataSource,
            requireArguments().getString(URL) ?: throw IllegalArgumentException("Use newInstance")
        )
    }

    private lateinit var binding: BottomSheetLayoutBinding

    override fun onAttach(context: Context) {
        (context.applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }
}
