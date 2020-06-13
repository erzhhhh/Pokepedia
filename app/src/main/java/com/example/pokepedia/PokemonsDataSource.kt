package com.example.pokepedia

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.PokemonModel

class PokemonsDataSource(
    private val service: PokemonService
) : PageKeyedDataSource<Int, PokemonModel>() {

    private var nextPageUrl = ""
    private var previousPageUrl = ""

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PokemonModel>
    ) {
        Log.i("йцйцйцйцйц", "loadInitial вызван")
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
        Log.i("йцйцйцйцйц", "loadAfter вызван")

        val currentPage = params.key
        val nextPage = currentPage + 1

        val list = service.getNextPage(nextPageUrl)
            .map {
                previousPageUrl = it.previousPageUrl
                nextPageUrl = it.nextPageUrl
                it.pokemonList
            }
            .blockingFirst()

        callback.onResult(list, nextPage)
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>) {
//        Log.i("йцйцйцйцйц", "loadBefore вызван")
//        val currentPage = params.key
//        val nextPage = currentPage - 1
//
//        val list = service.getNextPage(previousPageUrl)
//            .map {
//                previousPageUrl = it.previousPageUrl
//                nextPageUrl = it.nextPageUrl
//                it.pokemonList
//            }
//            .blockingFirst()
//
//        callback.onResult(list, nextPage)
    }
}