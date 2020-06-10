package com.example.pokepedia.models

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val count: Int,
    @SerializedName("next")
    val nextPageUrl: String,
    @SerializedName("previous")
    val previousPageUrl: String,
    @SerializedName("results")
    val pokemonList: List<Pokemon>
)

data class Pokemon(
    val name: Int,
    val url: String
)