package com.example.pokepedia.ext

import com.example.pokepedia.models.PokemonDetailModel

fun PokemonDetailModel.mapDetails(): PokemonDetailModel {
    var newHeight = ""
    var newWeight = ""

    try {
        val height = (this.height.toFloat() / 10).toString()
        newHeight = "$height Metre"
    } catch (e: NumberFormatException) {
        // not a valid int
    }

    try {
        val weight = (this.weight.toFloat() / 10).toString()
        newWeight = "$weight Kilogram"
    } catch (e: NumberFormatException) {
        // not a valid int
    }


    return this.apply {
        height = newHeight
        weight = newWeight
    }
}