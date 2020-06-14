package com.example.pokepedia.pokemonList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pokepedia.PokemonsDataFactory
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.NetworkState
import com.example.pokepedia.models.PokemonModel

class PokemonListViewModel(private val service: PokemonService) : ViewModel() {

    private var _childModels = MutableLiveData<PagedList<PokemonModel>>()
    var childModels: LiveData<PagedList<PokemonModel>> = _childModels

    private lateinit var networkState: LiveData<NetworkState>

    var factory: PokemonsDataFactory = PokemonsDataFactory(service)


    init {
        initialize()
    }

    fun currentNetworkState(): LiveData<NetworkState> = networkState

    private fun initialize() {
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(30)
            .setPageSize(20)
            .build()

        val dataSourceFactory = PokemonsDataFactory(service)

        childModels = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()

        networkState = Transformations.switchMap(dataSourceFactory.mutableDataSource) {
            it.initialLoad
        }
    }
}