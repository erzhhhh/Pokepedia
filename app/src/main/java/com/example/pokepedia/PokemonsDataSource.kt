package com.example.pokepedia

import androidx.paging.PageKeyedDataSource
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.PokemonModel


class PokemonsDataSource(
    private val service: PokemonService
) : PageKeyedDataSource<Int, PokemonModel>() {

    private var nextPageUrl: String? = null
    private var previousPageUrl: String? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PokemonModel>
    ) {
        val currentPage = 1
        val nextPage = currentPage + 1

        val list = service.getPokemonsInfo()
            .map {
                nextPageUrl = it.nextPageUrl
                it.pokemonList
            }
            .blockingFirst()

        callback.onResult(list, 0, nextPage)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>) {
        val currentPage = params.key
        val nextPage = currentPage + 1

        nextPageUrl?.let { url ->
            val list = service.getNextPage(url)
                .map {
                    previousPageUrl = it.previousPageUrl
                    nextPageUrl = it.nextPageUrl
                    it.pokemonList
                }
                .blockingFirst()

            callback.onResult(list, nextPage)
        }
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>) {
    }
}