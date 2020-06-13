package com.example.pokepedia.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokepedia.OnItemClickListener
import com.example.pokepedia.models.PokemonModel


@BindingAdapter(
    value = ["pokemonItems", "itemClickListener"],
    requireAll = true
)
fun setPagerItems(
    recyclerView: RecyclerView,
    offerPagerItems: List<PokemonModel>?,
    itemClickListener: OnItemClickListener<PokemonModel>
) {
    recyclerView.run {
        (adapter as? PokemonRecyclerViewAdapter ?: PokemonRecyclerViewAdapter(itemClickListener)
            .also { it.onItemClickListener = itemClickListener }
            .also { adapter = it })
            .setItems(offerPagerItems)

    }
}
