package com.example.pokepedia.models

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    val count: Int,
    @SerializedName("next")
    val nextPageUrl: String,
    @SerializedName("previous")
    val previousPageUrl: String,
    @SerializedName("results")
    val pokemonList: List<PokemonModel>
)

data class PokemonModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)


data class PokemonDetailModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("base_experience")
    val exp: String,
    @SerializedName("height")
    var height: String,
    @SerializedName("weight")
    var weight: String
)