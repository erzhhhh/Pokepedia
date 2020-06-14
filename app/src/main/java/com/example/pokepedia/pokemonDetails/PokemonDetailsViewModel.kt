package com.example.pokepedia.pokemonDetails

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

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    init {
        initialize()
    }

    @SuppressLint("CheckResult")
    fun initialize() {
        _isLoading.postValue(true)
        service.getPokemonDetails(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _childModel.value = it.mapDetails()
                    _isLoading.postValue(false)
                },
                {

                }
            )
    }
}