package com.example.pokepedia.pokemonList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.pokepedia.NewsDataFactory
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.PokemonModel

class PokemonListViewModel(private val service: PokemonService) : ViewModel() {

    private var _childModels = MutableLiveData<PagedList<PokemonModel>>()
    var childModels: LiveData<PagedList<PokemonModel>> = _childModels

    init {
        initialize()
    }

    private fun initialize() {

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(20)
            .setPageSize(20)
            .build()

        childModels = LivePagedListBuilder(NewsDataFactory(service), pagedListConfig).build()
    }
}