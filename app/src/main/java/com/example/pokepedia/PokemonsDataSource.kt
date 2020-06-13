package com.example.pokepedia

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.NetworkState
import com.example.pokepedia.models.PokemonModel


class PokemonsDataSource(
    private val service: PokemonService
) : PageKeyedDataSource<Int, PokemonModel>() {

    private val networkState: MutableLiveData<NetworkState> = MutableLiveData<NetworkState>()
    private var nextPageUrl: String? = null
    private var previousPageUrl: String? = null

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PokemonModel>
    ) {
        networkState.postValue(NetworkState.LOADING)

        val currentPage = 1
        val nextPage = currentPage + 1

        val list = service.getPokemonsInfo()
            .map {
                nextPageUrl = it.nextPageUrl
                it.pokemonList
            }
            .doOnNext { networkState.postValue(NetworkState.LOADED) }
            .doOnError {
                networkState.postValue(
                    NetworkState(
                        NetworkState.Status.FAILED,
                        it.message.orEmpty()
                    )
                )
            }
            .blockingFirst()

        callback.onResult(list, 0, nextPage)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>) {
        networkState.postValue(NetworkState.LOADING)

        val currentPage = params.key
        val nextPage = currentPage + 1

        nextPageUrl?.let { url ->
            val list = service.getNextPage(url)
                .map {
                    previousPageUrl = it.previousPageUrl
                    nextPageUrl = it.nextPageUrl
                    it.pokemonList
                }
                .doOnNext { networkState.postValue(NetworkState.LOADED) }
                .doOnError {
                    networkState.postValue(
                        NetworkState(
                            NetworkState.Status.FAILED,
                            it.message.orEmpty()
                        )
                    )
                }
                .blockingFirst()

            callback.onResult(list, nextPage)
        }
    }


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>) {
    }

    fun getNetworkState(): MutableLiveData<NetworkState> {
        return networkState
    }
}