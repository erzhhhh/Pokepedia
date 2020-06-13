package com.example.pokepedia.ext

import com.example.pokepedia.models.PokemonDetailModel

fun PokemonDetailModel.mapDetails(): PokemonDetailModel {
    var newHeight = ""
    var newWeight = ""

    try {
        newHeight = (this.height.toFloat() / 10).toString()
    } catch (e: NumberFormatException) {
        // not a valid int
    }

    try {
        newWeight = (this.weight.toFloat() / 10).toString()
    } catch (e: NumberFormatException) {
        // not a valid int
    }


    return this.apply {
        height = newHeight
        weight = newWeight
    }
}