package com.example.pokepedia.pokemonInfo

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokepedia.api.PokemonService
import com.example.pokepedia.ext.mapDetails
import com.example.pokepedia.models.PokemonDetailModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PokemonDetailsViewModel(
    private val service: PokemonService,
    private val url: String
) : ViewModel() {

    private var _childModel = MutableLiveData<PokemonDetailModel>()
    val childModel: LiveData<PokemonDetailModel>
        get() = _childModel

    private var _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    init {
        loadPokemonDetails()
    }

    @SuppressLint("CheckResult")
    fun loadPokemonDetails() {
        service.getPokemonProperties(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _childModel.value = it.mapDetails()
                    _name.value = it.name
                },
                {

                }
            )
    }
}