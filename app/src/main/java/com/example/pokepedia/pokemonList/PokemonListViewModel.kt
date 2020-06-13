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

    private var _networkState = MutableLiveData<NetworkState>()
    var networkState: LiveData<NetworkState> = _networkState

    var factory: PokemonsDataFactory = PokemonsDataFactory(service)

    init {
        initialize()
    }

    private fun initialize() {

        networkState = Transformations.switchMap(factory.getMutableLiveData()) {
            it.getNetworkState()
        }

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()

        childModels = LivePagedListBuilder(PokemonsDataFactory(service), pagedListConfig).build()
    }
}