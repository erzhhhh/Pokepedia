package com.example.pokepedia

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.NetworkState
import com.example.pokepedia.models.PokemonModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


open class PokemonsDataSource(
    private val service: PokemonService
) : PageKeyedDataSource<Int, PokemonModel>() {

    val initialLoad: MutableLiveData<NetworkState> = MutableLiveData()
    private var nextPageUrl: String? = null
    private var previousPageUrl: String? = null
    private var retry: (() -> Any)? = null

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.invoke()
    }

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
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    initialLoad.postValue(NetworkState.LOADED)
                    list.addAll(it.pokemonList)
                    nextPageUrl = it.nextPageUrl
                    retry = null

                    callback.onResult(list, 0, nextPage)
                },
                {
                    retry = {
                        loadInitial(params, callback)
                    }
                    initialLoad.value = NetworkState.error(it.message)
                }
            )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>) {
        initialLoad.postValue(NetworkState.LOADING)

        val currentPage = params.key
        val nextPage = currentPage + 1

        nextPageUrl?.let { url ->
            val list = mutableListOf<PokemonModel>()
            service.getNextPage(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        initialLoad.postValue(NetworkState.LOADED)
                        list.addAll(it.pokemonList)
                        previousPageUrl = it.previousPageUrl
                        nextPageUrl = it.nextPageUrl
                        retry = null

                        callback.onResult(list, nextPage)
                    },
                    {
                        retry = {
                            loadAfter(params, callback)
                        }
                        initialLoad.value = NetworkState.error(it.message)
                    }
                )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PokemonModel>) {
    }
}