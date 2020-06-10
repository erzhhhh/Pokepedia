package com.example.pokepedia.api

import com.example.pokepedia.models.PokemonResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface PokemonService {
    //    @GET("films/?format=json")
    @GET("v2/pokemon")
    fun getFilms(): Observable<PokemonResponse>

//    @GET("planets")
//    fun getPlanets(): Observable<Planets>
}