package com.example.pokepedia

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.PokemonModel


class PokemonsDataFactory(private val service: PokemonService) :
    DataSource.Factory<Int, PokemonModel>() {

    lateinit var moviesDataSource: PokemonsDataSource
    private var mutableLiveData = MutableLiveData<PokemonsDataSource>()

    override fun create(): DataSource<Int, PokemonModel> {
        moviesDataSource = PokemonsDataSource(service)
        mutableLiveData.postValue(moviesDataSource)
        return PokemonsDataSource(service)
    }

    fun getMutableLiveData(): MutableLiveData<PokemonsDataSource> {
        return mutableLiveData
    }
}