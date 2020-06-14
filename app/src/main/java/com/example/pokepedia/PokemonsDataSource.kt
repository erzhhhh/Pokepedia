package com.example.pokepedia

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.NetworkState
import com.example.pokepedia.models.PokemonModel


open class PokemonsDataSource(
    private val service: PokemonService
) : PageKeyedDataSource<Int, PokemonModel>() {

    val initialLoad: MutableLiveData<NetworkState> = MutableLiveData()
    private var nextPageUrl: String? = null
    private var previousPageUrl: String? = null

    @SuppressLint("CheckResult")
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PokemonModel>
    ) {
        initialLoad.postValue(NetworkState.LOADING)

        val currentPage = 1
        val nextPage = currentPage + 1

        val list = mutableListOf<PokemonModel>()
        service.getPokemonsInfo()
            .subscribe(
                {
                    if (it.pokemonList.isEmpty()) {
                        initialLoad.postValue(NetworkState.EMPTY)
                    } else {
                        initialLoad.postValue(NetworkState.LOADED)
                        list.addAll(it.pokemonList)
                    }
                    nextPageUrl = it.nextPageUrl
                },
                {

                }
            )

        callback.onResult(list, 0, nextPage)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>) {
        val currentPage = params.key
        val nextPage = currentPage + 1

        nextPageUrl?.let { url ->
            val list = mutableListOf<PokemonModel>()
            service.getNextPage(url)
                .subscribe(
                    {
                        previousPageUrl = it.previousPageUrl
                        nextPageUrl = it.nextPageUrl
                        list.addAll(it.pokemonList)
                    },
                    {

                    }
                )

            callback.onResult(list, nextPage)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>) {
    }
}