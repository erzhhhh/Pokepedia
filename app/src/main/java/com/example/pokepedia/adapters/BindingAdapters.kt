package com.example.pokepedia.adapters

import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.example.pokepedia.OnItemClickListener
import com.example.pokepedia.OnRetryClickListener
import com.example.pokepedia.R
import com.example.pokepedia.models.NetworkState
import com.example.pokepedia.models.PokemonModel
import com.example.pokepedia.pokemonDetails.PokemonDetailsViewModel


@BindingAdapter(
    value = ["pokemonItems", "itemClickListener", "retryClickListener", "networkState"],
    requireAll = true
)
fun setPagerItems(
    recyclerView: RecyclerView,
    offerPagerItems: PagedList<PokemonModel>?,
    onItemClickListener: OnItemClickListener<PokemonModel>,
    onRetryClickListener: OnRetryClickListener,
    networkState: NetworkState?
) {
    recyclerView.run {
        (adapter as? PokemonRecyclerViewAdapter ?: PokemonRecyclerViewAdapter(
            onItemClickListener,
            onRetryClickListener
        )
            .also { it.onItemClickListener = onItemClickListener }
            .also { it.onRetryClickListener = onRetryClickListener }
            .also { adapter = it })
            .also { it.submitList(offerPagerItems) }
            .also { it.setNetworkState(networkState) }
    }
}

@BindingAdapter(
    value = ["pokemonName", "pokemonIsLoading"],
    requireAll = true
)
fun bindName(
    textView: TextView,
    pokemonText: String?,
    isLoading: Boolean
) {
    if (isLoading) {
        textView.text = ""
    } else {
        textView.text = pokemonText
    }
}


@BindingAdapter(
    value = ["pokemonHeight", "pokemonIsLoading"],
    requireAll = true
)
fun bindHeight(
    textView: TextView,
    pokemonText: String?,
    isLoading: Boolean
) {
    if (isLoading) {
        textView.text = textView.context.getString(R.string.pokemon_height, "")
    } else {
        textView.text = textView.context.getString(R.string.pokemon_height, pokemonText.orEmpty())
    }
}

@BindingAdapter(
    value = ["pokemonWeight", "pokemonIsLoading"],
    requireAll = true
)
fun bindWeight(
    textView: TextView,
    pokemonText: String?,
    isLoading: Boolean
) {
    if (isLoading) {
        textView.text = textView.context.getString(R.string.pokemon_weight, "")
    } else {
        textView.text = textView.context.getString(R.string.pokemon_weight, pokemonText.orEmpty())
    }
}


@BindingAdapter(
    value = ["pokemonExp", "pokemonIsLoading"],
    requireAll = true
)
fun bindExp(
    textView: TextView,
    pokemonText: String?,
    isLoading: Boolean
) {
    if (isLoading) {
        textView.text = textView.context.getString(R.string.pokemon_exp, "")
    } else {
        textView.text = textView.context.getString(R.string.pokemon_exp, pokemonText.orEmpty())
    }
}


@BindingAdapter(value = ["isError"])
fun setErrorVisibility(
    textView: TextView,
    error: PokemonDetailsViewModel.PokemonError?
) {
    textView.isVisible = error?.isError ?: false
    textView.text = error?.message.orEmpty()
}
