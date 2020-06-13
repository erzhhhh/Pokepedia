package com.example.pokepedia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokepedia.OnItemClickListener
import com.example.pokepedia.databinding.ViewPokemonItemBinding
import com.example.pokepedia.models.PokemonModel

class PokemonRecyclerViewAdapter(
    var onItemClickListener: OnItemClickListener<PokemonModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = ArrayList<PokemonModel>()

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
        val pm = items[position]
        (holder as PokemonVH).bind(pm)
    }

    override fun getItemCount() = items.size

    fun setItems(items: List<PokemonModel>?) {
        this.items.clear()
        this.items.addAll(items.orEmpty())
        notifyDataSetChanged()
    }

    private class PokemonVH(
        private val binding: ViewPokemonItemBinding,
        onItemClickListener: OnItemClickListener<PokemonModel>
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemClickListener = onItemClickListener
        }

        fun bind(pm: PokemonModel) {
            binding.pokemon = pm
        }
    }
}
