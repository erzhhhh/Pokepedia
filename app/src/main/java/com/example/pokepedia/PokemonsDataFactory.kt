package com.example.pokepedia

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.PokemonModel


class PokemonsDataFactory(private val service: PokemonService) :
    DataSource.Factory<Int, PokemonModel>() {

    val mutableDataSource: MutableLiveData<PokemonsDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, PokemonModel> {
        val dataSource = PokemonsDataSource(service)
        mutableDataSource.postValue(dataSource)
        return dataSource
    }
}