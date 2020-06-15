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

open class PokemonListViewModel(private val service: PokemonService) : ViewModel() {

    private var _childModels = MutableLiveData<PagedList<PokemonModel>>()
    var childModels: LiveData<PagedList<PokemonModel>> = _childModels

    private lateinit var networkState: LiveData<NetworkState>
    private lateinit var factory: PokemonsDataFactory

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

        factory = PokemonsDataFactory(service)

        childModels = LivePagedListBuilder(factory, pagedListConfig).build()

        networkState = Transformations.switchMap(factory.mutableDataSource) {
            it.initialLoad
        }
    }

    fun retry() {
        factory.retry.invoke()
    }
}