package com.example.pokepedia.adapters

import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.example.pokepedia.OnItemClickListener
import com.example.pokepedia.models.NetworkState
import com.example.pokepedia.models.PokemonModel


@BindingAdapter(
    value = ["pokemonItems", "networkState", "itemClickListener"],
    requireAll = true
)
fun setPagerItems(
    recyclerView: RecyclerView,
    offerPagerItems: PagedList<PokemonModel>?,
    networkState: NetworkState?,
    itemClickListener: OnItemClickListener<PokemonModel>
) {
    recyclerView.run {
        (adapter as? PokemonRecyclerViewAdapter ?: PokemonRecyclerViewAdapter(itemClickListener)
            .also { it.onItemClickListener = itemClickListener }
            .also { adapter = it })
            .also { it.setNetworkState(networkState) }
            .also { it.submitList(offerPagerItems) }
    }
}
