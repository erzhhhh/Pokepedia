package com.example.pokepedia.pokemonList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokepedia.App
import com.example.pokepedia.OnItemClickListener
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.databinding.FragmentListBinding
import com.example.pokepedia.models.PokemonModel
import com.example.pokepedia.pokemonInfo.PokemonDetailsFragment
import javax.inject.Inject

class PokemonListFragment : Fragment() {

    @Inject
    lateinit var dataSource: PokemonService

    private val viewModel by viewModels<PokemonListViewModel> {
        PokemonListViewModelFactory(
            dataSource
        )
    }

    private lateinit var binding: FragmentListBinding

    override fun onAttach(context: Context) {
        (context.applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.itemClickListener = object : OnItemClickListener<PokemonModel?> {
            override fun onItemClick(item: PokemonModel?) {
                item?.url?.let {
                    PokemonDetailsFragment.newInstance(it)
                        .show(childFragmentManager, PokemonDetailsFragment::class.java.name)
                }
            }
        }
    }
}
