package com.example.pokepedia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokepedia.OnItemClickListener
import com.example.pokepedia.databinding.ViewPokemonItemBinding
import com.example.pokepedia.models.PokemonModel


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PokemonVH(
            ViewPokemonItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClickListener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is PokemonVH) {
            val pm = getItem(position)
            (holder as PokemonVH).bind(pm)
        }
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
}
