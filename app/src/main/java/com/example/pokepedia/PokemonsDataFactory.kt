package com.example.pokepedia

import androidx.paging.DataSource
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.PokemonModel


class PokemonsDataFactory(private val service: PokemonService) :
    DataSource.Factory<Int, PokemonModel>() {

    override fun create(): DataSource<Int, PokemonModel> {
        return PokemonsDataSource(service)
    }
}