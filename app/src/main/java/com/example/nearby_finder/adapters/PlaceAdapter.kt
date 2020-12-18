package com.example.nearby_finder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nearby_finder.data.PlaceItem
import com.example.nearby_finder.databinding.ViewPlaceBinding

class PlaceAdapter(): ListAdapter<PlaceItem, RecyclerView.ViewHolder>(PlaceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlaceViewHolder(
            ViewPlaceBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val placeItem: PlaceItem = getItem(position)
        (holder as PlaceViewHolder).bind(placeItem)
    }

    class PlaceViewHolder(
        private val binding: ViewPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                //TODO navigation to place fragment
            }
        }

        fun bind(item: PlaceItem) {
            binding.apply {
                place = item
                executePendingBindings()
            }
        }
    }

    private class PlaceDiffCallback : DiffUtil.ItemCallback<PlaceItem>() {

        override fun areItemsTheSame(oldItem: PlaceItem, newItem: PlaceItem): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PlaceItem, newItem: PlaceItem): Boolean {
            return oldItem == newItem
        }
    }
}