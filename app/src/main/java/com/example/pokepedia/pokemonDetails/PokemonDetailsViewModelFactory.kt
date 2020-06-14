package com.example.pokepedia.pokemonDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.pokepedia.api.PokemonService

class PokemonDetailsViewModelFactory(
    private val dataSource: PokemonService,
    private val url: String
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PokemonDetailsViewModel::class.java)) {
            return PokemonDetailsViewModel(dataSource, url) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}