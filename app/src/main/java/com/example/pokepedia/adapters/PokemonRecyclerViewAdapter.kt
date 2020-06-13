package com.example.pokepedia.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokepedia.OnItemClickListener
import com.example.pokepedia.R
import com.example.pokepedia.databinding.ViewPokemonItemBinding
import com.example.pokepedia.models.NetworkState
import com.example.pokepedia.models.PokemonModel


private const val POKEMON_ITEM_VIEW_TYPE = 1
private const val LOAD_ITEM_VIEW_TYPE = 0

class PokemonRecyclerViewAdapter(
    var onItemClickListener: OnItemClickListener<PokemonModel>
) : PagedListAdapter<PokemonModel, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PokemonModel>() {
            override fun areItemsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var networkState: NetworkState? = null

    override fun getItemViewType(position: Int): Int {
        return if (isLoadingData() && position == itemCount - 1) LOAD_ITEM_VIEW_TYPE else POKEMON_ITEM_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == POKEMON_ITEM_VIEW_TYPE) {
            PokemonVH(
                ViewPokemonItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onItemClickListener
            )
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_progress_item, parent, false)
            ProgressVH(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is PokemonVH) {
            val pm = getItem(position)
            (holder as PokemonVH).bind(pm)
        }
    }

    fun setNetworkState(networkState: NetworkState?) {
        networkState?.let {
            val wasLoading = isLoadingData()
            this.networkState = it
            val willLoad = isLoadingData()
            if (wasLoading != willLoad) {
                if (wasLoading) notifyItemRemoved(itemCount) else notifyItemInserted(itemCount)
            }
        }
    }

    private fun isLoadingData(): Boolean {
        return networkState != null && networkState !== NetworkState.LOADED
    }

    private class PokemonVH(
        private val binding: ViewPokemonItemBinding,
        onItemClickListener: OnItemClickListener<PokemonModel>
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemClickListener = onItemClickListener
        }

        fun bind(pm: PokemonModel?) {
            binding.pokemon = pm
        }
    }

    private class ProgressVH(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}
