package com.example.pokepedia.api

import com.example.pokepedia.models.PokemonDetailModel
import com.example.pokepedia.models.PokemonResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonService {

    @GET("v2/pokemon")
    fun getPokemonsInfo(): Observable<PokemonResponse>

    @GET
    fun getPokemonProperties(@Url url: String): Observable<PokemonDetailModel>

    @GET
    fun getNextPage(@Url url: String): Observable<PokemonResponse>
}