package com.example.nearby_finder.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nearby_finder.data.Place
import com.example.nearby_finder.databinding.ViewPlaceBinding

class PlaceAdapter(): ListAdapter<Place, RecyclerView.ViewHolder>(PlaceDiffCallback()) {

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
        val place: Place = getItem(position)
        (holder as PlaceViewHolder).bind(place)
    }

    class PlaceViewHolder(
        private val binding: ViewPlaceBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                //TODO navigation to place fragment
            }
        }

        fun bind(item: Place) {
            binding.apply {
                place = item
                executePendingBindings()
            }
        }
    }

    private class PlaceDiffCallback : DiffUtil.ItemCallback<Place>() {

        override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
            return oldItem == newItem
        }
    }
}