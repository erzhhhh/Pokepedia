package com.example.pokepedia.pokemonList

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.models.PokemonModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PokemonListViewModel(private val service: PokemonService) : ViewModel() {

    private var _childModels = MutableLiveData<List<PokemonModel>>()
    val childModels: LiveData<List<PokemonModel>>
        get() = _childModels

    init {
        loadPokemonList()
    }

    @SuppressLint("CheckResult")
    fun loadPokemonList() {
        service.getPokemonsInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _childModels.value = it.pokemonList
                },
                {

                }
            )
    }
}