package com.example.nearby_finder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nearby_finder.data.DummyData
import com.example.nearby_finder.databinding.ViewPlaceBinding

class PlaceAdapter(): ListAdapter<DummyData, RecyclerView.ViewHolder>(PlaceDiffCallback()) {

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
        val place: DummyData = getItem(position)
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

        fun bind(item: DummyData) {
            binding.apply {
                place = item
                executePendingBindings()
            }
        }
    }

    private class PlaceDiffCallback : DiffUtil.ItemCallback<DummyData>() {

        override fun areItemsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: DummyData, newItem: DummyData): Boolean {
            return oldItem == newItem
        }
    }
}