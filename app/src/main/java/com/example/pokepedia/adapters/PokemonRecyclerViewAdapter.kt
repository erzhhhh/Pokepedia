package com.example.pokepedia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokepedia.OnItemClickListener
import com.example.pokepedia.OnRetryClickListener
import com.example.pokepedia.databinding.ViewLoadingItemBinding
import com.example.pokepedia.databinding.ViewPokemonItemBinding
import com.example.pokepedia.models.NetworkState
import com.example.pokepedia.models.PokemonModel

private const val TYPE_NETWORK_STATE = 0
private const val TYPE_ITEM = 1

class PokemonRecyclerViewAdapter(
    var onItemClickListener: OnItemClickListener<PokemonModel>,
    var onRetryClickListener: OnRetryClickListener
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

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return when {
            hasExtraRow() && position == itemCount - 1 -> TYPE_NETWORK_STATE
            else -> TYPE_ITEM
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> {
                PokemonVH(
                    ViewPokemonItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onItemClickListener
                )
            }
            TYPE_NETWORK_STATE -> {
                NetworkStateVH(
                    ViewLoadingItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onRetryClickListener
                )
            }
            else -> {
                throw Exception("Not a valid view type")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PokemonVH) {
            val pm = getItem(position)
            holder.bind(pm)
        }
        if (holder is NetworkStateVH) {
            holder.bind()
        }
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = networkState
        val hadExtraRow = hasExtraRow()
        networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemChanged(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    inner class PokemonVH(
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

    inner class NetworkStateVH(
        private val binding: ViewLoadingItemBinding,
        onRetryListener: OnRetryClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemRetryClickListener = onRetryListener
        }

        fun bind() {
            networkState?.let {

                if (it == NetworkState.LOADING) {
                    binding.progressBar.isVisible = true
                    binding.retryButton.isVisible = false
                    binding.errorTextView.isVisible = false
                }

                if (it.status == NetworkState.Status.FAILED) {
                    binding.progressBar.isVisible = false
                    binding.retryButton.isVisible = true
                    binding.errorTextView.isVisible = true
                    binding.errorTextView.text = it.message
                }
            }
        }
    }
}
